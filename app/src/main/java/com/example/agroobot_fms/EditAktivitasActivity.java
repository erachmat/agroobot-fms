package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_activity.CreateActivityResponse;
import com.example.agroobot_fms.model.get_one.Activity;
import com.example.agroobot_fms.model.update_activity.UpdateActivityBody;
import com.example.agroobot_fms.model.update_activity.UpdateActivityResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAktivitasActivity extends AppCompatActivity {

    EditText etNamaAktivitas, etNamaBahan, etDosis, etJumlahHst, etSatuanHst;
    LinearLayout btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aktivitas);

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

                Activity data = gson.fromJson(dataJson, Activity.class);

                initView(data, idPetani, idLahan, idPeriode,
                        tokenLogin, fullnameVar);

            }
        }
    }

    private void initView(Activity data, String idPetani,
                          String idLahan, String idPeriode, String tokenLogin,
                          String fullnameVar) {

        etNamaAktivitas = findViewById(R.id.et_nama_aktivitas);
        etNamaBahan = findViewById(R.id.et_nama_bahan);
        etDosis = findViewById(R.id.et_dosis);
        etJumlahHst = findViewById(R.id.et_jumlah_hst);
        etSatuanHst = findViewById(R.id.et_satuan_hst);

        etNamaAktivitas.setText(data.getActivityTxt());
        etNamaBahan.setText(data.getMaterialTxt());
        etDosis.setText(data.getDoseTxt());
        etJumlahHst.setText(String.valueOf(data.getJumlahTxt()));
        etSatuanHst.setText(data.getSatuanTxt());

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormActivity()) {

                    ProgressDialog progressDialog = new ProgressDialog(
                            EditAktivitasActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    String namaAktivitas = etNamaAktivitas.getText().toString().trim();
                    String namaBahan = etNamaBahan.getText().toString().trim();
                    String dosis = etDosis.getText().toString().trim();
                    String jumlahHst = etJumlahHst.getText().toString().trim();
                    String satuanHst = etSatuanHst.getText().toString().trim();

                    UpdateActivityBody updateActivityBody = new UpdateActivityBody();
                    updateActivityBody.setUserIdInt(idPetani);
                    updateActivityBody.setLandCodeVar(idLahan);
                    updateActivityBody.setPeriodPlantTxt(idPeriode);
                    updateActivityBody.setActivityTxt(namaAktivitas);
                    updateActivityBody.setMaterialTxt(namaBahan);
                    updateActivityBody.setDoseTxt(dosis);
                    updateActivityBody.setJumlahTxt(jumlahHst);
                    updateActivityBody.setSatuanTxt(satuanHst);
                    updateActivityBody.setUpdatedByVar(fullnameVar);

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<UpdateActivityResponse> updateActivityResponseCall =
                            service.updateActivity(Integer.parseInt(data.getIdSeq()),
                                    tokenLogin, updateActivityBody);
                    updateActivityResponseCall.enqueue(new Callback<UpdateActivityResponse>() {
                        @Override
                        public void onResponse(Call<UpdateActivityResponse> call,
                                               Response<UpdateActivityResponse> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

                                        finish();
                                        Toast.makeText(EditAktivitasActivity.this,
                                                "Silahkan refresh list activity!",
                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        SharedPreferences sh = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(
                                                EditAktivitasActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

//                                    String message = response.body().getMessage();
//                                    Toast.makeText(EditAktivitasActivity.this, message,
//                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditAktivitasActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditAktivitasActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateActivityResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditAktivitasActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });

                }
            }
        });
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

}