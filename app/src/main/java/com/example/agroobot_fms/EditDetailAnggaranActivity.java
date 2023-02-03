package com.example.agroobot_fms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.dropdown_activity.DropdownActivity;
import com.example.agroobot_fms.model.dropdown_category.Datum;
import com.example.agroobot_fms.model.dropdown_category.DropdownCategory;
import com.example.agroobot_fms.model.get_one_budget_detail.GetOneBudgetDetail;
import com.example.agroobot_fms.model.update_budget_detail.UpdateBudgetDetail;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDetailAnggaranActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;

    private SmartMaterialSpinner<String> spKategori;
    private SmartMaterialSpinner<String> spKegiatan;
    private SmartMaterialSpinner<String> spSatuan;

    EditText etLuas, etJumlah, etHarga;
    ImageView imgBrowsePhoto, btnBack;
    TextView btnBrowsePhoto;
    private ProgressDialog progressDialog;
    LinearLayout btnSimpan;

    private SharedPreferences sh;
    String tokenLogin, fullnameVar;
    private Bitmap imgDokumentasi;
    private ArrayList<String> satuanList;
    private ArrayList<String> kategoriList;
    private ArrayList<String> kegiatanList;
    private String idSatuan, idKategori, idKegiatan;
    private Integer idInt;

    Call<UpdateBudgetDetail> updateBudgetDetailCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_anggaran);

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        tokenLogin = sh.getString("tokenLogin", "");
        fullnameVar = sh.getString("fullnameVar", "");

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");
            String idAnggaran = getIntent().getStringExtra("idAnggaran");

            initView(idSeq, idAnggaran);
            initSpinner(idSeq);
            initData(idSeq);
        }
    }

    private void initSpinner(String idSeq) {

        spKategori = findViewById(R.id.sp_kategori);
        spKegiatan = findViewById(R.id.sp_kegiatan);
        spSatuan = findViewById(R.id.sp_satuan);

        initDataKategori();

        satuanList = new ArrayList<>();
        satuanList.add("Kilogram");
        satuanList.add("Unit");
        satuanList.add("Paket");
        satuanList.add("HOK");

        spSatuan.setItem(satuanList);
        spSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idSatuan = satuanList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idKategori = kategoriList.get(i);
                initDataKegiatan(idKategori);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKegiatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idKegiatan = kegiatanList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDataKegiatan(String idKategori) {

        progressDialog = new ProgressDialog(EditDetailAnggaranActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownActivity> dropdownActivityCall = service.dropdownActivity(tokenLogin,
                idKategori);
        dropdownActivityCall.enqueue(new Callback<DropdownActivity>() {
            @Override
            public void onResponse(Call<DropdownActivity> call,
                                   Response<DropdownActivity> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_activity.Datum> listData =
                                    response.body().getData();

                            kegiatanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kegiatanList.add(listData.get(i).getActivityTxt());
                            }

                            spKegiatan.setItem(kegiatanList);

//                            Toast.makeText(EditDetailAnggaranActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(EditDetailAnggaranActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDetailAnggaranActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditDetailAnggaranActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownActivity> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditDetailAnggaranActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void initDataKategori() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownCategory> dropdownCategoryCall = service.dropdownCategory(tokenLogin);
        dropdownCategoryCall.enqueue(new Callback<DropdownCategory>() {
            @Override
            public void onResponse(Call<DropdownCategory> call, Response<DropdownCategory> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<Datum> listData = response.body().getData();

                            kategoriList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kategoriList.add(listData.get(i).getCategoryVar());
                            }

                            spKategori.setItem(kategoriList);

//                            Toast.makeText(EditDetailAnggaranActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(EditDetailAnggaranActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDetailAnggaranActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditDetailAnggaranActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownCategory> call, Throwable t) {
                Toast.makeText(EditDetailAnggaranActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void initView(String idSeq, String idAnggaran) {

        etLuas = findViewById(R.id.et_luas);
        etJumlah = findViewById(R.id.et_jumlah);
        etHarga = findViewById(R.id.et_harga);
        imgBrowsePhoto = findViewById(R.id.img_browse_photo);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBrowsePhoto = findViewById(R.id.txt_browse_photo);
        btnBrowsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
            }
        });

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllField()) {

                    progressDialog = new ProgressDialog(EditDetailAnggaranActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    RequestBody budgetIdInt = createPartFromString(String.valueOf(idInt));
                    RequestBody activityTxt = createPartFromString(idKegiatan);
                    RequestBody categoryVar = createPartFromString(idKategori);
                    RequestBody satuanVar = createPartFromString(idSatuan);

                    RequestBody areaVar = createPartFromString(etLuas.getText().toString());
                    RequestBody quantityVar = createPartFromString(etJumlah.getText().toString());
                    RequestBody priceVar = createPartFromString(etHarga.getText().toString());

                    RequestBody updateByVar = createPartFromString(fullnameVar);

                    if(imgDokumentasi != null) {
                        // convert gambar jadi File terlebih dahulu dengan
                        // memanggil createTempFile yang di atas tadi.
                        File file = createTempFile(imgDokumentasi);
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part images = MultipartBody.Part.createFormData("images",
                                file.getName(), reqFile);

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        updateBudgetDetailCall = service.updateBudgetDetail(
                                Integer.parseInt(idSeq), tokenLogin, budgetIdInt, activityTxt, categoryVar,
                                areaVar, quantityVar, satuanVar, priceVar, images, updateByVar);


                    } else {

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        updateBudgetDetailCall = service.updateBudgetDetailWithoutImage(
                                Integer.parseInt(idSeq), tokenLogin, budgetIdInt, activityTxt, categoryVar,
                                areaVar, quantityVar, satuanVar, priceVar, updateByVar);

                    }

                    updateBudgetDetailCall.enqueue(new Callback<UpdateBudgetDetail>() {
                        @Override
                        public void onResponse(Call<UpdateBudgetDetail> call,
                                               Response<UpdateBudgetDetail> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

//                                        Intent intent = new Intent(
//                                                EditDetailAnggaranActivity.this,
//                                                HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
//
//                                        finish();

                                        Intent intent = new Intent(
                                                EditDetailAnggaranActivity.this,
                                                DetailAnggaranPetaniActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("idSeq", idAnggaran);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        sh = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(
                                                EditDetailAnggaranActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    String message = response.body().getMessage();
                                    Toast.makeText(EditDetailAnggaranActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                    Log.e("EDIT_BUDGET_DETAIL", String.valueOf(
                                            response.body().getCode()));
                                } else {
                                    Toast.makeText(EditDetailAnggaranActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditDetailAnggaranActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateBudgetDetail> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditDetailAnggaranActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });
                }
            }
        });
    }

    private void initData(String idSeq) {

        progressDialog = new ProgressDialog(EditDetailAnggaranActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetOneBudgetDetail> getOneBudgetDetailCall = service.getOneBudgetDetail(tokenLogin,
                Integer.parseInt(idSeq));
        getOneBudgetDetailCall.enqueue(new Callback<GetOneBudgetDetail>() {
            @Override
            public void onResponse(Call<GetOneBudgetDetail> call,
                                   Response<GetOneBudgetDetail> response) {
                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            com.example.agroobot_fms.model.get_one_budget_detail.Datum dataItem
                                    = response.body().getData().get(0);

                            idInt = dataItem.getBudgetIdInt();

                            idKategori = dataItem.getCategoryVar();
                            int indexKategori = kategoriList.indexOf(idKategori);
                            spKategori.setSelection(indexKategori);

//                            idKegiatan = dataItem.getActivityTxt();
//                            int indexKegiatan = kegiatanList.indexOf(idKegiatan);
//                            spKegiatan.setSelection(indexKegiatan);

                            etLuas.setText(dataItem.getAreaVar());
                            etJumlah.setText(dataItem.getQuantityVar());
                            etHarga.setText(dataItem.getPriceVar());

                            idSatuan = dataItem.getSatuanVar();
                            int indexSatuan = satuanList.indexOf(idSatuan);
                            spSatuan.setSelection(indexSatuan);

                            if(!dataItem.getDocumentTxt().equals("")) {

                                new GetImageFromUrl(imgBrowsePhoto)
                                        .execute(dataItem.getDocumentTxt());

//                                Picasso.Builder builder = new Picasso.Builder(
//                                        EditDataPanenActivity.this);
//                                builder.downloader(new OkHttp3Downloader(
//                                        EditDataPanenActivity.this));
//                                builder.build().load(dataItem.getDocumentTxt().get(0))
//                                        .error(R.drawable.img_dokumentasi)
//                                        .into(imgBrowsePhoto);
                            }

//                            Toast.makeText(EditDetailAnggaranActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();
                        } else {

                            sh = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    EditDetailAnggaranActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(EditDetailAnggaranActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDetailAnggaranActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditDetailAnggaranActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneBudgetDetail> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditDetailAnggaranActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
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
        Intent intent = new Intent(EditDetailAnggaranActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(EditDetailAnggaranActivity.this,
                ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
                ImagePickerActivity.REQUEST_GALLERY_IMAGE);

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
                    imgDokumentasi = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(), uri);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditDetailAnggaranActivity.this);
        builder.setTitle("Grant Permissions");
        builder.setMessage("This app needs permission to use this feature. " +
                "You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel),
                (dialog, which) -> dialog.cancel());
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

    private boolean checkAllField() {

        if(idKategori == null || idKategori.equals("")) {
            Toast.makeText(EditDetailAnggaranActivity.this,
                    "Pilih kategori terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idKegiatan == null || idKegiatan.equals("")) {
            Toast.makeText(EditDetailAnggaranActivity.this,
                    "Pilih kegiatan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idSatuan == null || idSatuan.equals("")) {
            Toast.makeText(EditDetailAnggaranActivity.this,
                    "Pilih satuan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etHarga.length() == 0) {
            etHarga.setError("This field is required");
            return false;
        }

        if (etJumlah.length() == 0) {
            etJumlah.setError("This field is required");
            return false;
        }

        if (etLuas.length() == 0) {
            etLuas.setError("This field is required");
            return false;
        }

//        if(imgDokumentasi == null) {
//            Toast.makeText(EditDetailAnggaranActivity.this,
//                    "Masukkan gambar dokumentasi terlebih dahulu",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }
}