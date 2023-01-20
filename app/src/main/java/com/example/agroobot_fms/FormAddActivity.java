package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_activity.CreateActivityBody;
import com.example.agroobot_fms.model.create_activity.CreateActivityResponse;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAddActivity extends AppCompatActivity {

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

    private CardView cvKosong, cvActivity, cvPengamatan, cvDokumentasi;
    private ScrollView scrollView;
    private LinearLayout btnAddActivity, btnAddPengamatan, btnDokumentasi;
    EditText etNamaAktivitas, etNamaBahan, etDosis, etJumlahHst, etSatuanHst, etCatatan;
    private ProgressDialog progressDialog;

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
                                    String message = response.body().getMessage();

                                    if(response.body().getCode() == 0) {

                                    }

                                    Toast.makeText(FormAddActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
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

                                    }

                                    Toast.makeText(FormAddActivity.this, message,
                                            Toast.LENGTH_SHORT).show();

                                    Gson gson = new Gson();
                                    String json = gson.toJson(createObservationBody);
                                    Log.e("TOKEN", tokenLogin);
                                    Log.e("OBSERVATION", json);
                                    Log.e("CODE", String.valueOf(response.code()));

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
}