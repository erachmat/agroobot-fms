package com.example.agroobot_fms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_one_data_panen.Data;
import com.example.agroobot_fms.model.get_one_data_panen.DataPanen;
import com.example.agroobot_fms.model.update_data_panen.UpdateDataPanen;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataPanenActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;

    private SmartMaterialSpinner<String> spKomoditas;
    private SmartMaterialSpinner<String> spLahan;
    private SmartMaterialSpinner<String> spPeriodeTanam;

    LinearLayout lytTglPanen;
    LinearLayout lytTglPenggilingan;
    LinearLayout lytTglPenjemuran;
    LinearLayout btnEdit;

    TextView txtTglPanen;
    TextView txtTglPenggilingan;
    TextView txtTglPenjemuran;
    TextView txtBrowsePhoto;

    EditText etHasilPanen;
    EditText etHasilPenggilingan;
    EditText etHasilPenjemuran;

    ImageView imgBrowsePhoto, btnBack;

    private ProgressDialog progressDialog;

    private ArrayList<String> komoditasList, lahanList, periodeList;
    String idLahan, idKomoditas, idPeriode;
    String tokenLogin;
    SharedPreferences sh;
    Bitmap imgDokumentasi;
    private SimpleDateFormat simpleDateFormat;
    String tglPanen, tglPenggilingan, tglPenjemuran;
    String fullnameVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_panen);

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        tokenLogin = sh.getString("tokenLogin", "");
        fullnameVar = sh.getString("fullnameVar", "");

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");

            initView(idSeq);
            initSpinner();
            initData(idSeq);
        }
    }

    private void initData(String idSeq) {

        progressDialog = new ProgressDialog(EditDataPanenActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DataPanen> dataPanenCall = service.getOneDataPanen(tokenLogin, Integer.parseInt(idSeq));
        dataPanenCall.enqueue(new Callback<DataPanen>() {
            @Override
            public void onResponse(Call<DataPanen> call, Response<DataPanen> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            Data dataItem = response.body().getData();

                            idKomoditas = dataItem.getCommodityNameVar();
                            int indexKomoditas = komoditasList.indexOf(idKomoditas);
                            spKomoditas.setSelection(indexKomoditas);

                            idLahan = dataItem.getLandCodeVar();
                            int indexLahan = lahanList.indexOf(idLahan);
                            spLahan.setSelection(indexLahan);

                            idPeriode = dataItem.getPeriodPlantTxt();
                            int indexPeriode = periodeList.indexOf(idPeriode);
                            spPeriodeTanam.setSelection(indexPeriode);

                            etHasilPanen.setText(String.valueOf(dataItem.getHarvestFlo()));

                            tglPanen = dataItem.getHarvestOnDte().substring(0, 10);
                            tglPanen = tglPanen.replace("-", "/");
                            txtTglPanen.setText(tglPanen);

                            etHasilPenjemuran.setText(String.valueOf(
                                    dataItem.getHarvestDryingFlo()));

                            tglPenjemuran = dataItem.getHarvestDryingDte().substring(0, 10);
                            tglPenjemuran = tglPenjemuran.replace("-", "/");
                            txtTglPenjemuran.setText(tglPenjemuran);

                            etHasilPenggilingan.setText(String.valueOf(
                                    dataItem.getHarvestMillingFlo()));

                            tglPenggilingan = dataItem.getHarvestMillingDte().
                                    substring(0, 10);
                            tglPenggilingan = tglPenggilingan.replace("-", "/");
                            txtTglPenggilingan.setText(tglPenggilingan);

                            if(dataItem.getDocumentTxt().size() != 0) {

                                new GetImageFromUrl(imgBrowsePhoto)
                                        .execute(dataItem.getDocumentTxt().get(0));

//                                Picasso.Builder builder = new Picasso.Builder(
//                                        EditDataPanenActivity.this);
//                                builder.downloader(new OkHttp3Downloader(
//                                        EditDataPanenActivity.this));
//                                builder.build().load(dataItem.getDocumentTxt().get(0))
//                                        .error(R.drawable.img_dokumentasi)
//                                        .into(imgBrowsePhoto);
                            }

                        } else {

                            sh = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    EditDataPanenActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(EditDataPanenActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDataPanenActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditDataPanenActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPanen> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditDataPanenActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void initView(String idSeq) {

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lytTglPanen = findViewById(R.id.lyt_tgl_panen);
        lytTglPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar("panen");
            }
        });

        lytTglPenggilingan = findViewById(R.id.lyt_tgl_penggilingan);
        lytTglPenggilingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar("penggilingan");
            }
        });

        lytTglPenjemuran = findViewById(R.id.lyt_tgl_penjemuran);
        lytTglPenjemuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar("penjemuran");
            }
        });

        txtTglPanen = findViewById(R.id.txt_tgl_panen);
        txtTglPenggilingan = findViewById(R.id.txt_tgl_penggilingan);
        txtTglPenjemuran = findViewById(R.id.txt_tgl_penjemuran);

        txtBrowsePhoto = findViewById(R.id.txt_browse_photo);
        txtBrowsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
            }
        });

        etHasilPanen = findViewById(R.id.et_hasil_panen);
        etHasilPenjemuran = findViewById(R.id.et_hasil_penjemuran);
        etHasilPenggilingan = findViewById(R.id.et_hasil_penggilingan);

        imgBrowsePhoto = findViewById(R.id.img_browse_photo);

        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllField()) {

                    progressDialog = new ProgressDialog(EditDataPanenActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    RequestBody commodityNameVar = createPartFromString(idKomoditas);
                    RequestBody landCodeVar = createPartFromString(idLahan);
                    RequestBody periodPlantText = createPartFromString(idPeriode);

                    RequestBody harvestFlo = createPartFromString(etHasilPanen.getText().toString());
                    RequestBody harvestOnDte = createPartFromString(tglPanen);

                    RequestBody harvestDryingflo = createPartFromString(
                            etHasilPenjemuran.getText().toString());
                    RequestBody harvestDryingDte = createPartFromString(tglPenjemuran);

                    RequestBody harvestMillingFlo = createPartFromString(etHasilPenggilingan.
                            getText().toString());
                    RequestBody harvestMilingDte = createPartFromString(tglPenggilingan);

                    // convert gambar jadi File terlebih dahulu dengan
                    // memanggil createTempFile yang di atas tadi.
                    File file = createTempFile(imgDokumentasi);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part images = MultipartBody.Part.createFormData("images",
                            file.getName(), reqFile);

                    RequestBody createdByVar = createPartFromString(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<UpdateDataPanen> updateDataPanenCall =
                            service.updateDataPanen(Integer.parseInt(idSeq), tokenLogin,
                                    commodityNameVar, landCodeVar,
                                    periodPlantText, harvestFlo, harvestOnDte, harvestDryingflo,
                                    harvestDryingDte, harvestMillingFlo, harvestMilingDte,
                                    images, createdByVar);
                    updateDataPanenCall.enqueue(new Callback<UpdateDataPanen>() {
                        @Override
                        public void onResponse(Call<UpdateDataPanen> call,
                                               Response<UpdateDataPanen> response) {
                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    String message = response.body().getMessage();
                                    if(response.body().getCode() == 0) {

                                        Intent intent = new Intent(
                                                EditDataPanenActivity.this,
                                                HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("viewpager_position", 2);
                                        startActivity(intent);

                                        finish();
                                    }

                                    Toast.makeText(EditDataPanenActivity.this, message,
                                            Toast.LENGTH_SHORT).show();

//                                    Gson gson = new Gson();
//                                    String json = gson.toJson(createObservationBody);
//                                    Log.e("TOKEN", tokenLogin);
//                                    Log.e("OBSERVATION", json);
//                                    Log.e("CODE", String.valueOf(response.code()));

                                } else {
                                    Toast.makeText(EditDataPanenActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditDataPanenActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateDataPanen> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditDataPanenActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });

                }
            }
        });
    }

    private void openCalendar(String opsi) {
        // on below line we are getting
        // the instance of our calendar.
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                EditDataPanenActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar mCalendar = Calendar.getInstance();
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).
                                format(mCalendar.getTime());

                        if(opsi.equals("panen")) {

                            txtTglPanen.setText(selectedDate);

                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            tglPanen = simpleDateFormat.format(mCalendar.getTime());
                        }

                        if(opsi.equals("penggilingan")) {

                            txtTglPenggilingan.setText(selectedDate);

                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            tglPenggilingan = simpleDateFormat.format(mCalendar.getTime());
                        }

                        if(opsi.equals("penjemuran")) {

                            txtTglPenjemuran.setText(selectedDate);

                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            tglPenjemuran = simpleDateFormat.format(mCalendar.getTime());
                        }

                        // on below line we are setting date to our text view.
//                        selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);

        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }

    private void initSpinner() {

        spKomoditas = findViewById(R.id.sp_komoditas);
        spLahan = findViewById(R.id.sp_lahan);
        spPeriodeTanam = findViewById(R.id.sp_periode_tanam);

        komoditasList = new ArrayList<>();
        komoditasList.add("PADI");
        komoditasList.add("KACANG PANJANG");

        lahanList = new ArrayList<>();
        lahanList.add("01001");
        lahanList.add("03003");

        periodeList = new ArrayList<>();
        periodeList.add("3");
        periodeList.add("2201");

        spKomoditas.setItem(komoditasList);
        spLahan.setItem(lahanList);
        spPeriodeTanam.setItem(periodeList);

        spKomoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                idKomoditas = komoditasList.get(position);

//                Toast.makeText(getActivity(), idPetani,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spLahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {

                idLahan = lahanList.get(i);

//                Toast.makeText(getActivity(), idLahan,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spPeriodeTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {

                idPeriode = periodeList.get(i);

//                Toast.makeText(getActivity(), idPeriode,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void pickImg() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void showImagePickerOptions() {

        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();

            }
        });

    }

    private void launchCameraIntent() {
        Intent intent = new Intent(EditDataPanenActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(EditDataPanenActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    imgDokumentasi = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    imgBrowsePhoto.setImageBitmap(imgDokumentasi);

                    // loading profile image from local cache
//                    loadProfile(uri.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPanenActivity.this);
        builder.setTitle("Grant Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /*
    TODO mengconvert Bitmap menjadi file dikarenakan retrofit
     hanya mengenali tipe file untuk upload gambarnya sekaligus
     mengcompressnya menjadi WEBP dikarenakan size bisa sangat kecil
     dan kualitasnya pun setara dengan PNG.
*/
    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.webp");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    private boolean checkAllField() {

        if(idKomoditas == null || idKomoditas.equals("")) {
            Toast.makeText(EditDataPanenActivity.this, "Pilih komoditas terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idLahan == null || idLahan.equals("")) {
            Toast.makeText(EditDataPanenActivity.this, "Pilih lahan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idPeriode == null || idPeriode.equals("")) {
            Toast.makeText(EditDataPanenActivity.this, "Pilih periode terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtTglPanen.length() == 0 || txtTglPanen.getText() == "dd/mm/yyyy") {
            Toast.makeText(EditDataPanenActivity.this,
                    "Pilih tanggal panen terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etHasilPanen.length() == 0) {
            etHasilPanen.setError("This field is required");
            return false;
        }

        if (txtTglPenjemuran.length() == 0 || txtTglPenjemuran.getText() == "dd/mm/yyyy") {
            Toast.makeText(EditDataPanenActivity.this,
                    "Pilih tanggal penjemuran terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etHasilPenjemuran.length() == 0) {
            etHasilPenjemuran.setError("This field is required");
            return false;
        }

        if (txtTglPenggilingan.length() == 0 || txtTglPenggilingan.getText() == "dd/mm/yyyy") {
            Toast.makeText(EditDataPanenActivity.this,
                    "Pilih tanggal penggilingan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etHasilPenggilingan.length() == 0) {
            etHasilPenggilingan.setError("This field is required");
            return false;
        }

        if(imgDokumentasi == null) {
            Toast.makeText(EditDataPanenActivity.this,
                    "Masukkan gambar dokumentasi terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public GetImageFromUrl(ImageView img){
            this.imageView = img;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            imgDokumentasi = bitmap;

            imageView.setImageBitmap(bitmap);
        }
    }
}