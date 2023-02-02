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
import android.net.Uri;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_activity.CreateActivityBody;
import com.example.agroobot_fms.model.create_activity.CreateActivityResponse;
import com.example.agroobot_fms.model.create_documentation.CreateDocumentation;
import com.example.agroobot_fms.model.create_observation.CreateObservation;
import com.example.agroobot_fms.model.create_observation.CreateObservationBody;
import com.example.agroobot_fms.model.dropdown_kondisi_air.KondisiAir;
import com.example.agroobot_fms.model.dropdown_kondisi_anakan.KondisiAnakan;
import com.example.agroobot_fms.model.dropdown_kondisi_butir.KondisiButir;
import com.example.agroobot_fms.model.dropdown_kondisi_daun.Datum;
import com.example.agroobot_fms.model.dropdown_kondisi_daun.KondisiDaun;
import com.example.agroobot_fms.model.dropdown_kondisi_lahan.KondisiLahan;
import com.example.agroobot_fms.model.login.LoginResponse;
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

public class FormAddActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;
    private SmartMaterialSpinner<String> spPilihActivity;
    private SmartMaterialSpinner<String> spKondisiDaun;
    private SmartMaterialSpinner<String> spKondisiPengairan;
    private SmartMaterialSpinner<String> spKondisiBulir;
    private SmartMaterialSpinner<String> spKondisiLahan;
    private SmartMaterialSpinner<String> spKondisiAnakan;
    private SmartMaterialSpinner<String> spHama;

    private List<String> kondisiHamaList;
    private List<String> kondisiDaunList;
    private List<String> kondisiPengairanList;
    private List<String> kondisiBulirList;
    private List<String> kondisiLahanList;
    private List<String> kondisiAnakanList;
    private List<String> activityList;

    SharedPreferences sh;
    String tokenLogin;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    private String activity;
    private String kondisiHama;
    private String kondisiDaun;
    private String kondisiPengairan;
    private String kondisiLahan;
    private String kondisiBulir;
    private String kondisiAnakan;
    String idPetani, idLahan, idPeriode, fullnameVar;
    Bitmap imgDokumentasi;

    private CardView cvKosong, cvActivity, cvPengamatan, cvDokumentasi;
    private ScrollView scrollView;
    private LinearLayout btnAddActivity, btnAddPengamatan, btnDokumentasi;
    EditText etNamaAktivitas, etNamaBahan, etDosis, etJumlahHst, etSatuanHst, etCatatan;
    private ProgressDialog progressDialog;
    TextView txtBrowsePhoto;
    ImageView imgBrowsePhoto, btnBack;
    Spinner spDaun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add);

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        tokenLogin = sh.getString("tokenLogin", "");
        idPetani = sh.getString("idPetani", "");
        idLahan = sh.getString("idLahan", "");
        idPeriode = sh.getString("idPeriode", "");
        fullnameVar = sh.getString("fullnameVar", "");

        calendar = Calendar.getInstance();

        initView();
        initSpinner();
    }

    private void initView() {

        imgBrowsePhoto = findViewById(R.id.img_browse_photo);

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

        etNamaAktivitas = findViewById(R.id.et_nama_aktivitas);
        etNamaBahan = findViewById(R.id.et_nama_bahan);
        etDosis = findViewById(R.id.et_dosis);
        etJumlahHst = findViewById(R.id.et_jumlah_hst);
        etSatuanHst = findViewById(R.id.et_satuan_hst);
        etCatatan = findViewById(R.id.et_catatan);

        cvKosong = findViewById(R.id.cv_kosong);
        cvActivity = findViewById(R.id.cv_activity);
        cvPengamatan = findViewById(R.id.cv_pengamatan);
        cvDokumentasi = findViewById(R.id.cv_dokumentasi);

        btnAddActivity = findViewById(R.id.btn_add_activity);
        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormActivity()) {

                    progressDialog = new ProgressDialog(FormAddActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    String namaAktivitas = etNamaAktivitas.getText().toString().trim();
                    String namaBahan = etNamaBahan.getText().toString().trim();
                    String dosis = etDosis.getText().toString().trim();
                    String jumlahHst = etJumlahHst.getText().toString().trim();
                    String satuanHst = etSatuanHst.getText().toString().trim();

                    CreateActivityBody createActivityBody = new CreateActivityBody();
                    createActivityBody.setUserIdInt(idPetani);
                    createActivityBody.setLandCodeVar(idLahan);
                    createActivityBody.setPeriodPlantTxt(idPeriode);
                    createActivityBody.setActivityTxt(namaAktivitas);
                    createActivityBody.setMaterialTxt(namaBahan);
                    createActivityBody.setDoseTxt(dosis);
                    createActivityBody.setJumlahTxt(jumlahHst);
                    createActivityBody.setSatuanTxt(satuanHst);
                    createActivityBody.setCreatedByVar(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<CreateActivityResponse> createActivityResponseCall =
                            service.createActivity(tokenLogin, createActivityBody);
                    createActivityResponseCall.enqueue(new Callback<CreateActivityResponse>() {
                        @Override
                        public void onResponse(Call<CreateActivityResponse> call,
                                               Response<CreateActivityResponse> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

                                        finish();
                                        Toast.makeText(FormAddActivity.this,
                                                "Silahkan refresh list activity!",
                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        sh = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(FormAddActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

//                                    String message = response.body().getMessage();
//                                    Toast.makeText(FormAddActivity.this, message,
//                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormAddActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(FormAddActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateActivityResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(FormAddActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });
                }
            }
        });

        btnAddPengamatan = findViewById(R.id.btn_add_pengamatan);
        btnAddPengamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormPengamatan()) {

                    progressDialog = new ProgressDialog(FormAddActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.format(calendar.getTime());

                    String catatanPengamatan = etCatatan.getText().toString();

                    CreateObservationBody createObservationBody = new CreateObservationBody();
                    createObservationBody.setUserIdInt(idPetani);
                    createObservationBody.setLandCodeVar(idLahan);
                    createObservationBody.setPeriodPlantTxt(idPeriode);

                    createObservationBody.setLeafConditionTxt(kondisiDaun);
                    createObservationBody.setLandConditionTxt(kondisiLahan);
                    createObservationBody.setWateringConditionTxt(kondisiPengairan);
                    createObservationBody.setGrainConditionTxt(kondisiBulir);
                    createObservationBody.setPuppiesConditionTxt(kondisiAnakan);
                    createObservationBody.setHamaTxt(kondisiHama);
                    createObservationBody.setExampleObservationTxt(catatanPengamatan);
                    createObservationBody.setTimeCalenderDte(date);

                    createObservationBody.setCreatedByVar(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<CreateObservation> createObservationCall =
                            service.createObservation(tokenLogin, createObservationBody);
                    createObservationCall.enqueue(new Callback<CreateObservation>() {
                        @Override
                        public void onResponse(Call<CreateObservation> call,
                                               Response<CreateObservation> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    String message = response.body().getMessage();

                                    if(response.body().getCode() == 0) {
                                        finish();
                                        Toast.makeText(FormAddActivity.this,
                                                "Silahkan refresh list pengamatan!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(FormAddActivity.this, message,
                                            Toast.LENGTH_SHORT).show();

//                                    Gson gson = new Gson();
//                                    String json = gson.toJson(createObservationBody);
//                                    Log.e("TOKEN", tokenLogin);
//                                    Log.e("OBSERVATION", json);
//                                    Log.e("CODE", String.valueOf(response.code()));

                                } else {
                                    Toast.makeText(FormAddActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(FormAddActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateObservation> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(FormAddActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });

                }
            }
        });

        btnDokumentasi = findViewById(R.id.btn_add_dokumentasi);
        btnDokumentasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgDokumentasi != null) {

                    progressDialog = new ProgressDialog(FormAddActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.format(calendar.getTime());

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

                    RequestBody createdByVar = createPartFromString(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<CreateDocumentation> createDocumentationCall =
                            service.createDocumentation(tokenLogin, userIdInt, landCodeVar, periodPlantText,
                                    timeCalendarDte, images, createdByVar);
                    createDocumentationCall.enqueue(new Callback<CreateDocumentation>() {
                        @Override
                        public void onResponse(Call<CreateDocumentation> call, Response<CreateDocumentation> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    String message = response.body().getMessage();
                                    if(response.body().getCode() == 0) {
                                        finish();
                                        Toast.makeText(FormAddActivity.this,
                                                "Silahkan refresh list dokumentasi!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(FormAddActivity.this, message,
                                            Toast.LENGTH_SHORT).show();

//                                    Gson gson = new Gson();
//                                    String json = gson.toJson(createObservationBody);
//                                    Log.e("TOKEN", tokenLogin);
//                                    Log.e("OBSERVATION", json);
//                                    Log.e("CODE", String.valueOf(response.code()));

                                } else {
                                    Toast.makeText(FormAddActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(FormAddActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateDocumentation> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(FormAddActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });

                }
                else {
                    Toast.makeText(FormAddActivity.this,
                            "Silahkan masukkan gambar terlebih dahulu!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        scrollView = findViewById(R.id.scroll_view);
    }

    private boolean checkFormPengamatan() {

        if(kondisiDaun == null || kondisiDaun.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi daun terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiPengairan == null || kondisiPengairan.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi pengairan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiLahan == null || kondisiLahan.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi lahan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiBulir == null || kondisiBulir.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi bulir terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiAnakan == null || kondisiAnakan.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi anakan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiHama == null || kondisiHama.equals("")) {
            Toast.makeText(FormAddActivity.this, "Pilih kondisi hama terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etCatatan.length() == 0) {
            etCatatan.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }

    private boolean checkFormActivity() {

        if (etNamaAktivitas.length() == 0) {
            etNamaAktivitas.setError("This field is required");
            return false;
        }

        if (etNamaBahan.length() == 0) {
            etNamaBahan.setError("This field is required");
            return false;
        }

        if (etDosis.length() == 0) {
            etDosis.setError("This field is required");
            return false;
        }

        if (etJumlahHst.length() == 0) {
            etJumlahHst.setError("This field is required");
            return false;
        }

        if (etSatuanHst.length() == 0) {
            etSatuanHst.setError("This field is required");
            return false;
        }

        return true;
    }

    private void initSpinner() {

        spPilihActivity = findViewById(R.id.sp_pilih_activity);
        spKondisiDaun = findViewById(R.id.sp_kondisi_daun);
        spKondisiPengairan = findViewById(R.id.sp_kondisi_pengairan);
        spKondisiLahan = findViewById(R.id.sp_kondisi_lahan);
        spKondisiBulir = findViewById(R.id.sp_kondisi_bulir);
        spKondisiAnakan = findViewById(R.id.sp_kondisi_anakan);
        spHama = findViewById(R.id.sp_hama);
        spDaun = findViewById(R.id.sp_daun);

        activityList = new ArrayList<>();
        activityList.add("Aktivitas");
        activityList.add("Pengamatan");
        activityList.add("Dokumentasi");

        spPilihActivity.setItem(activityList);
        spPilihActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity = activityList.get(i);
                setView(activity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDaun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiDaun = kondisiDaunList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKondisiDaun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiDaun = kondisiDaunList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKondisiPengairan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiPengairan = kondisiPengairanList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKondisiLahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiLahan = kondisiLahanList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKondisiBulir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiBulir = kondisiBulirList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spKondisiAnakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiAnakan = kondisiAnakanList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        kondisiHamaList = new ArrayList<>();
        kondisiHamaList.add("Tidak ada");
        kondisiHamaList.add("Ada");

        spHama.setItem(kondisiHamaList);
        spHama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiHama = kondisiHamaList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setView(String activity) {

        cvKosong.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        switch (activity) {
            case "Aktivitas":
                cvActivity.setVisibility(View.VISIBLE);
                btnAddActivity.setVisibility(View.VISIBLE);

                cvPengamatan.setVisibility(View.GONE);
                btnAddPengamatan.setVisibility(View.GONE);
                cvDokumentasi.setVisibility(View.GONE);
                btnDokumentasi.setVisibility(View.GONE);
                break;

            case "Pengamatan":
                cvPengamatan.setVisibility(View.VISIBLE);
                btnAddPengamatan.setVisibility(View.VISIBLE);

                cvActivity.setVisibility(View.GONE);
                btnAddActivity.setVisibility(View.GONE);
                cvDokumentasi.setVisibility(View.GONE);
                btnDokumentasi.setVisibility(View.GONE);

                setSpKondisiDaun();
                setSpKondisiPengairan();
                setSpKondisiLahan();
                setSpKondisiBulir();
                setSpKondisiAnakan();
                break;

            case "Dokumentasi":
                cvDokumentasi.setVisibility(View.VISIBLE);
                btnDokumentasi.setVisibility(View.VISIBLE);

                cvPengamatan.setVisibility(View.GONE);
                btnAddPengamatan.setVisibility(View.GONE);
                cvActivity.setVisibility(View.GONE);
                btnAddActivity.setVisibility(View.GONE);
                break;
        }
    }

    private void setSpKondisiAnakan() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<KondisiAnakan> kondisiAnakanCall = service.dropdownKondisiAnakan(tokenLogin);
        kondisiAnakanCall.enqueue(new Callback<KondisiAnakan>() {
            @Override
            public void onResponse(Call<KondisiAnakan> call, Response<KondisiAnakan> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_kondisi_anakan.Datum>
                                    listData = response.body().getData();

                            kondisiAnakanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiAnakanList.add(listData.get(i).getPuppiesConditionVar());
                            }

                            spKondisiAnakan.setItem(kondisiAnakanList);
                        }

                        Log.e("SP_KONDISI_ANAKAN", message);

                    } else {
                        Toast.makeText(FormAddActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FormAddActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiAnakan> call, Throwable t) {
                Toast.makeText(FormAddActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiBulir() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<KondisiButir> kondisiButirCall = service.dropdownKondisiButir(tokenLogin);
        kondisiButirCall.enqueue(new Callback<KondisiButir>() {
            @Override
            public void onResponse(Call<KondisiButir> call, Response<KondisiButir> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_kondisi_butir.Datum>
                                    listData = response.body().getData();

                            kondisiBulirList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiBulirList.add(listData.get(i).getGrainConditionVar());
                            }

                            spKondisiBulir.setItem(kondisiBulirList);
                        }

                        Log.e("SP_KONDISI_BULIR", message);

                    } else {
                        Toast.makeText(FormAddActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FormAddActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiButir> call, Throwable t) {
                Toast.makeText(FormAddActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiLahan() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<KondisiLahan> kondisiLahanCall = service.dropdownKondisiLahan(tokenLogin);
        kondisiLahanCall.enqueue(new Callback<KondisiLahan>() {
            @Override
            public void onResponse(Call<KondisiLahan> call, Response<KondisiLahan> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {
                            List<com.example.agroobot_fms.model.dropdown_kondisi_lahan.Datum>
                                    listData = response.body().getData();

                            kondisiLahanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiLahanList.add(listData.get(i).getLandConditionVar());
                            }

                            spKondisiLahan.setItem(kondisiLahanList);
                        }

                        Log.e("SP_KONDISI_LAHAN", message);

                    } else {
                        Toast.makeText(FormAddActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FormAddActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiLahan> call, Throwable t) {
                Toast.makeText(FormAddActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpKondisiPengairan() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<KondisiAir> kondisiAirCall = service.dropdownKondisiAir(tokenLogin);
        kondisiAirCall.enqueue(new Callback<KondisiAir>() {
            @Override
            public void onResponse(Call<KondisiAir> call, Response<KondisiAir> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {
                            List<com.example.agroobot_fms.model.dropdown_kondisi_air.Datum> listData
                                    = response.body().getData();

                            kondisiPengairanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiPengairanList.add(listData.get(i).getWateringConditionVar());
                            }

                            spKondisiPengairan.setItem(kondisiPengairanList);
                        }

                        Log.e("SP_KONDISI_AIR", message);

                    } else {
                        Toast.makeText(FormAddActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FormAddActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiAir> call, Throwable t) {
                Toast.makeText(FormAddActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiDaun() {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<KondisiDaun> kondisiDaunCall = service.dropdownKondisiDaun(tokenLogin);
        kondisiDaunCall.enqueue(new Callback<KondisiDaun>() {
            @Override
            public void onResponse(Call<KondisiDaun> call, Response<KondisiDaun> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {
                            List<Datum> listData = response.body().getData();

//                            Toast.makeText(FormAddActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            kondisiDaunList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiDaunList.add(listData.get(i).getLeafConditionVar());
                            }

                            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),
                                    listData);
                            spDaun.setAdapter(customAdapter);

                            spKondisiDaun.setItem(kondisiDaunList);
                        }

                        Log.e("SP_KONDISI_DAUN", message);

//                        Toast.makeText(FormAddActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FormAddActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FormAddActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiDaun> call, Throwable t) {
                Toast.makeText(FormAddActivity.this,
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
        Intent intent = new Intent(FormAddActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(FormAddActivity.this, ImagePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(FormAddActivity.this);
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
}