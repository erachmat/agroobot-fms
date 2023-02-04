package com.example.agroobot_fms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_one.Documentation;
import com.example.agroobot_fms.model.update_activity.UpdateActivityResponse;
import com.example.agroobot_fms.model.update_documentation.UpdateDocumentationResponse;
import com.google.gson.Gson;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDokumentasiActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;

    Bitmap imgDokumentasi;
    Calendar calendar;

    TextView txtBrowsePhoto;
    ImageView imgBrowsePhoto, btnBack;
    LinearLayout btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dokumentasi);

        calendar = Calendar.getInstance();

        if (getIntent().getExtras() != null) {

            SharedPreferences sh = getSharedPreferences("MySharedPref",
                    Context.MODE_PRIVATE);
            String tokenLogin = sh.getString("tokenLogin", "");
            String fullnameVar = sh.getString("fullnameVar", "");

            String dataJson = getIntent().getStringExtra("dataJson");
            String idPetani = getIntent().getStringExtra("idPetani");
            String idLahan = getIntent().getStringExtra("idLahan");
            String idPeriode = getIntent().getStringExtra("idPeriode");

            if (dataJson != null) {

                Gson gson = new Gson();

                Documentation data = gson.fromJson(dataJson, Documentation.class);

                initView(data, idPetani, idLahan, idPeriode,
                        tokenLogin, fullnameVar);

            }
        }
    }

    private void initView(Documentation data, String idPetani, String idLahan,
                          String idPeriode, String tokenLogin, String fullnameVar) {

        imgBrowsePhoto = findViewById(R.id.img_browse_photo);

        if(data.getDocumentTxt().size() != 0) {

            new GetImageFromUrl(imgBrowsePhoto)
                    .execute(data.getDocumentTxt().get(0));

//                                Picasso.Builder builder = new Picasso.Builder(
//                                        EditDataPanenActivity.this);
//                                builder.downloader(new OkHttp3Downloader(
//                                        EditDataPanenActivity.this));
//                                builder.build().load(dataItem.getDocumentTxt().get(0))
//                                        .error(R.drawable.img_dokumentasi)
//                                        .into(imgBrowsePhoto);
        }

        txtBrowsePhoto = findViewById(R.id.txt_browse_photo);
        txtBrowsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
            }
        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgDokumentasi != null) {

                    ProgressDialog progressDialog = new ProgressDialog(
                            EditDokumentasiActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = simpleDateFormat.format(calendar.getTime());

                    RequestBody userIdInt = createPartFromString(idPetani);
                    RequestBody landCodeVar = createPartFromString(idLahan);
                    RequestBody periodPlantText = createPartFromString(idPeriode);
                    RequestBody timeCalendarDte = createPartFromString(date);

                    //convert gambar jadi File terlebih dahulu dengan
                    // memanggil createTempFile yang di atas tadi.
                    File file = createTempFile(imgDokumentasi);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part images = MultipartBody.Part.createFormData("images",
                            file.getName(), reqFile);

                    RequestBody updateByVar = createPartFromString(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<UpdateDocumentationResponse> updateDocumentationResponseCall =
                            service.updateDocumetation(Integer.parseInt(data.getIdSeq()),
                                    tokenLogin, userIdInt, landCodeVar, periodPlantText,
                                    timeCalendarDte, images, updateByVar);
                    updateDocumentationResponseCall.enqueue(new Callback<UpdateDocumentationResponse>() {
                        @Override
                        public void onResponse(Call<UpdateDocumentationResponse> call,
                                               Response<UpdateDocumentationResponse> response) {
                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

                                        Intent intent = new Intent(
                                                EditDokumentasiActivity.this,
                                                JadwalActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                        finish();

//                                        finish();
//                                        Toast.makeText(EditDokumentasiActivity.this,
//                                                "Silahkan refresh list dokumentasi!",
//                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        SharedPreferences sh = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(
                                                EditDokumentasiActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

//                                    String message = response.body().getMessage();
//                                    Toast.makeText(EditDokumentasiActivity.this, message,
//                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditDokumentasiActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditDokumentasiActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateDocumentationResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditDokumentasiActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });


                }
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
        Intent intent = new Intent(EditDokumentasiActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(EditDokumentasiActivity.this, ImagePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDokumentasiActivity.this);
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