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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_one.Rating;
import com.example.agroobot_fms.model.update_rating.UpdateRatingBody;
import com.example.agroobot_fms.model.update_rating.UpdateRatingResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSaranActivity extends AppCompatActivity {

    private SmartMaterialSpinner<String> spNilai;
    EditText etSaran;
    ImageView btnBack;
    private LinearLayout btnAddSaran;

    private List<String> listPenilaian;

    private SharedPreferences sh;
    private Calendar calendar;
    private String nilaiPetani;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_saran);

        if (getIntent().getExtras() != null) {

            SharedPreferences sh = getSharedPreferences("MySharedPref",
                    Context.MODE_PRIVATE);
            String tokenLogin = sh.getString("tokenLogin", "");
            String fullnameVar = sh.getString("fullnameVar", "");

            String dataJson = getIntent().getStringExtra("dataJson");
            String idPetani = getIntent().getStringExtra("idPetani");
            String idLahan = getIntent().getStringExtra("idLahan");
            String idPeriode = getIntent().getStringExtra("idPeriode");

//            Log.e("idPetani", String.valueOf(idPetani));
//            Log.e("idLahan", String.valueOf(idLahan));
//            Log.e("idPeriode", String.valueOf(idPeriode));

            calendar = Calendar.getInstance();

            if (dataJson != null) {

                Gson gson = new Gson();

                Rating data = gson.fromJson(dataJson, Rating.class);

                initView(data, idPetani, idLahan, idPeriode,
                        tokenLogin, fullnameVar);

            }
        }


    }

    private void initView(Rating data, String idPetani, String idLahan,
                          String idPeriode, String tokenLogin, String fullnameVar) {

        etSaran = findViewById(R.id.et_saran);
        etSaran.setText(data.getSuggestTxt());

        spNilai = findViewById(R.id.sp_nilai_petani);

        listPenilaian = new ArrayList<>();
        listPenilaian.add("Tidak Dilakukan");
        listPenilaian.add("Dilakukan");
        spNilai.setItem(listPenilaian);

        nilaiPetani = data.getRatingTxt();
        int indexHama = listPenilaian.indexOf(nilaiPetani);
        spNilai.setSelection(indexHama);

        spNilai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nilaiPetani = listPenilaian.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddSaran = findViewById(R.id.btn_simpan_saran);
        btnAddSaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(EditSaranActivity.this,
//                        "Pilih nilai petani terlebih dahulu",
//                        Toast.LENGTH_SHORT).show();

                if(checkFormSaran()) {

                    ProgressDialog progressDialog = new ProgressDialog(
                            EditSaranActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.format(calendar.getTime());

                    UpdateRatingBody updateRatingBody = new UpdateRatingBody();
                    updateRatingBody.setUserIdInt(idPetani);
                    updateRatingBody.setLandCodeVar(idLahan);
                    updateRatingBody.setPeriodPlantTxt(idPeriode);
                    updateRatingBody.setTimeCalenderDte(date);

                    updateRatingBody.setSuggestTxt(etSaran.getText().toString());
                    updateRatingBody.setRatingTxt(nilaiPetani);

                    updateRatingBody.setUpdatedByVar(fullnameVar);

                    SharedPreferences sh = getSharedPreferences("MySharedPref",
                            Context.MODE_PRIVATE);
                    String tokenLogin = sh.getString("tokenLogin", "");

//                    Toast.makeText(EditSaranActivity.this,
//                            data.getIdSeq(),
//                        Toast.LENGTH_SHORT).show();
//
//                    Log.e("idPetani", String.valueOf(idPetani));
//                    Log.e("idLahan", String.valueOf(idLahan));
//                    Log.e("idPeriode", String.valueOf(idPeriode));

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<UpdateRatingResponse> updateRatingResponseCall =
                            service.updateRating(Integer.parseInt(data.getIdSeq()),
                                    tokenLogin, updateRatingBody);
                    updateRatingResponseCall.enqueue(new Callback<UpdateRatingResponse>() {
                        @Override
                        public void onResponse(Call<UpdateRatingResponse> call,
                                               Response<UpdateRatingResponse> response) {

                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    if(response.body().getCode() == 0) {

                                        Intent intent = new Intent(
                                                EditSaranActivity.this,
                                                JadwalActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                        finish();

//                                        finish();
//                                        Toast.makeText(EditSaranActivity.this,
//                                                "Silahkan refresh list pengamatan!",
//                                                Toast.LENGTH_SHORT).show();

                                    }
//                                    else {
//
//                                        SharedPreferences sh = getSharedPreferences(
//                                                "MySharedPref", Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = sh.edit();
//                                        editor.putBoolean("isUserLogin", false);
//                                        editor.apply();
//
//                                        Intent intent = new Intent(
//                                                EditSaranActivity.this,
//                                                LoginActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
//                                        finish();
//                                    }

                                    String message = response.body().getMessage();
                                    Toast.makeText(EditSaranActivity.this, message,
                                            Toast.LENGTH_SHORT).show();

                                    Log.e("EDIT SARAN", String.valueOf(response.body().getCode()));
                                } else {
                                    Toast.makeText(EditSaranActivity.this,
                                            "1",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditSaranActivity.this,
                                        "2",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateRatingResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditSaranActivity.this,
                                    "3",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });

                }
            }
        });
    }

    private boolean checkFormSaran() {

        if (etSaran.length() == 0) {
            etSaran.setError("This field is required");
            return false;
        }

        if(nilaiPetani == null || nilaiPetani.equals("")) {
            Toast.makeText(EditSaranActivity.this,
                    "Pilih nilai petani terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}