package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_observation.CreateObservation;
import com.example.agroobot_fms.model.create_observation.CreateObservationBody;
import com.example.agroobot_fms.model.dropdown_kondisi_air.KondisiAir;
import com.example.agroobot_fms.model.dropdown_kondisi_anakan.KondisiAnakan;
import com.example.agroobot_fms.model.dropdown_kondisi_butir.KondisiButir;
import com.example.agroobot_fms.model.dropdown_kondisi_daun.Datum;
import com.example.agroobot_fms.model.dropdown_kondisi_daun.KondisiDaun;
import com.example.agroobot_fms.model.dropdown_kondisi_lahan.KondisiLahan;
import com.example.agroobot_fms.model.get_one.Observation;
import com.example.agroobot_fms.model.update_observation.UpdateObservationBody;
import com.example.agroobot_fms.model.update_observation.UpdateObservationResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPengamatanActivity extends AppCompatActivity {

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

    EditText etCatatan;
    LinearLayout btnSimpan;

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    private String kondisiHama;
    private String kondisiDaun;
    private String kondisiPengairan;
    private String kondisiLahan;
    private String kondisiBulir;
    private String kondisiAnakan;
    String idPetani, idLahan, idPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengamatan);

        if (getIntent().getExtras() != null) {

            SharedPreferences sh = getSharedPreferences("MySharedPref",
                    Context.MODE_PRIVATE);
            String tokenLogin = sh.getString("tokenLogin", "");
            String fullnameVar = sh.getString("fullnameVar", "");

            String dataJson = getIntent().getStringExtra("dataJson");
            String idPetani = getIntent().getStringExtra("idPetani");
            String idLahan = getIntent().getStringExtra("idLahan");
            String idPeriode = getIntent().getStringExtra("idPeriode");

            calendar = Calendar.getInstance();

            if (dataJson != null) {

                Gson gson = new Gson();

                Observation data = gson.fromJson(dataJson, Observation.class);

                initView(data, idPetani, idLahan, idPeriode,
                        tokenLogin, fullnameVar);
            }
        }
    }

    private void initView(Observation data, String idPetani, String idLahan,
                          String idPeriode, String tokenLogin, String fullnameVar) {

        spKondisiDaun = findViewById(R.id.sp_kondisi_daun);
        spKondisiPengairan = findViewById(R.id.sp_kondisi_pengairan);
        spKondisiLahan = findViewById(R.id.sp_kondisi_lahan);
        spKondisiBulir = findViewById(R.id.sp_kondisi_bulir);
        spKondisiAnakan = findViewById(R.id.sp_kondisi_anakan);
        spHama = findViewById(R.id.sp_hama);

        setSpKondisiDaun(tokenLogin, data);
        setSpKondisiPengairan(tokenLogin, data);
        setSpKondisiLahan(tokenLogin, data);
        setSpKondisiBulir(tokenLogin, data);
        setSpKondisiAnakan(tokenLogin, data);

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

        kondisiHama = data.getHamaTxt();
        int indexHama = kondisiHamaList.indexOf(kondisiHama);
        spHama.setSelection(indexHama);

        spHama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kondisiHama = kondisiHamaList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etCatatan = findViewById(R.id.et_catatan);
        etCatatan.setText(data.getExampleObservationTxt());

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormPengamatan()) {

                    ProgressDialog progressDialog = new ProgressDialog(
                            EditPengamatanActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.format(calendar.getTime());

                    String catatanPengamatan = etCatatan.getText().toString();

                    UpdateObservationBody updateObservationBody = new UpdateObservationBody();
                    updateObservationBody.setUserIdInt(idPetani);
                    updateObservationBody.setLandCodeVar(idLahan);
                    updateObservationBody.setPeriodPlantTxt(idPeriode);

                    updateObservationBody.setLeafConditionTxt(kondisiDaun);
                    updateObservationBody.setLandConditionTxt(kondisiLahan);
                    updateObservationBody.setWateringConditionTxt(kondisiPengairan);
                    updateObservationBody.setGrainConditionTxt(kondisiBulir);
                    updateObservationBody.setPuppiesConditionTxt(kondisiAnakan);
                    updateObservationBody.setHamaTxt(kondisiHama);
                    updateObservationBody.setExampleObservationTxt(catatanPengamatan);
                    updateObservationBody.setTimeCalenderDte(date);

                    updateObservationBody.setUpdatedByVar(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<UpdateObservationResponse> updateObservationResponseCall =
                            service.updateObservation(Integer.parseInt(data.getIdSeq()),
                                    tokenLogin, updateObservationBody);
                    updateObservationResponseCall.enqueue(new Callback<UpdateObservationResponse>() {
                        @Override
                        public void onResponse(Call<UpdateObservationResponse> call,
                                               Response<UpdateObservationResponse> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

                                        finish();
                                        Toast.makeText(EditPengamatanActivity.this,
                                                "Silahkan refresh list pengamatan!",
                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        SharedPreferences sh = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(
                                                EditPengamatanActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

//                                    String message = response.body().getMessage();
//                                    Toast.makeText(EditPengamatanActivity.this, message,
//                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditPengamatanActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditPengamatanActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateObservationResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditPengamatanActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });


                }
            }
        });
    }

    private void setSpKondisiAnakan(String tokenLogin, Observation data) {

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

                            kondisiAnakan = data.getPuppiesConditionTxt();
                            int indexAnakan = kondisiAnakanList.indexOf(kondisiAnakan);
                            spKondisiAnakan.setSelection(indexAnakan);
                        }

                        Log.e("SP_KONDISI_ANAKAN", message);

                    } else {
                        Toast.makeText(EditPengamatanActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPengamatanActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiAnakan> call, Throwable t) {
                Toast.makeText(EditPengamatanActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiBulir(String tokenLogin, Observation data) {

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

                            kondisiBulir = data.getGrainConditionTxt();
                            int indexBulir = kondisiBulirList.indexOf(kondisiBulir);
                            spKondisiBulir.setSelection(indexBulir);
                        }

                        Log.e("SP_KONDISI_BULIR", message);

                    } else {
                        Toast.makeText(EditPengamatanActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPengamatanActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiButir> call, Throwable t) {
                Toast.makeText(EditPengamatanActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiLahan(String tokenLogin, Observation data) {

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

                            kondisiLahan = data.getLandConditionTxt();
                            int indexLahan = kondisiLahanList.indexOf(kondisiLahan);
                            spKondisiLahan.setSelection(indexLahan);
                        }

                        Log.e("SP_KONDISI_LAHAN", message);

                    } else {
                        Toast.makeText(EditPengamatanActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPengamatanActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiLahan> call, Throwable t) {
                Toast.makeText(EditPengamatanActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpKondisiPengairan(String tokenLogin, Observation data) {

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

                            kondisiPengairan = data.getWateringConditionTxt();
                            int indexPengairan = kondisiPengairanList.indexOf(kondisiPengairan);
                            spKondisiPengairan.setSelection(indexPengairan);
                        }

                        Log.e("SP_KONDISI_AIR", message);

                    } else {
                        Toast.makeText(EditPengamatanActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPengamatanActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiAir> call, Throwable t) {
                Toast.makeText(EditPengamatanActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void setSpKondisiDaun(String tokenLogin, Observation data) {

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

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            kondisiDaunList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                kondisiDaunList.add(listData.get(i).getLeafConditionVar());
                            }

                            spKondisiDaun.setItem(kondisiDaunList);

                            kondisiDaun = data.getLeafConditionTxt();
                            int indexDaun = kondisiDaunList.indexOf(kondisiDaun);
                            spKondisiDaun.setSelection(indexDaun);
                        }

                        Log.e("SP_KONDISI_DAUN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPengamatanActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPengamatanActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KondisiDaun> call, Throwable t) {
                Toast.makeText(EditPengamatanActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private boolean checkFormPengamatan() {

        if(kondisiDaun == null || kondisiDaun.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi daun terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiPengairan == null || kondisiPengairan.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi pengairan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiLahan == null || kondisiLahan.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi lahan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiBulir == null || kondisiBulir.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi bulir terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiAnakan == null || kondisiAnakan.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi anakan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(kondisiHama == null || kondisiHama.equals("")) {
            Toast.makeText(EditPengamatanActivity.this, "Pilih kondisi hama terlebih dahulu",
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

}