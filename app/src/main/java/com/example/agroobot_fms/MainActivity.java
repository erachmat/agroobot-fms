package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_data_panen.CreateDataPanen;
import com.example.agroobot_fms.model.delete_data_panen.DeleteDataPanen;
import com.example.agroobot_fms.model.login.LoginBody;
import com.example.agroobot_fms.model.login.LoginResponse;
import com.example.agroobot_fms.model.update_data_panen.UpdateDataPanen;
import com.example.agroobot_fms.utils.SessionManager;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String token;
    GetService service;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        token = preferences.getString("TOKEN",null);
        token = "Bearer " + token;

        service = ApiClient.getRetrofitInstance().create(GetService.class);

        id = 15;

        EditText etName = findViewById(R.id.et_name);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String username = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                LoginBody loginBody = new LoginBody();
                loginBody.setPhoneNumberInt(username);
                loginBody.setPasswordVar(password);

                GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                Call<LoginResponse> call = service.loginAccount(loginBody);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this,
                                    response.body().getMessage(), Toast.LENGTH_LONG).show();
                            SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                            preferences.edit().putString("TOKEN", response.body().getData().getToken()).apply();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                t.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

//        Button btnCreate = findViewById(R.id.btn_create);
//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setMessage("Loading....");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//
//                String one = "PADI";
//                String two = "01001";
//                String three = "3";
//                String four = "100";
//                String five = "2022-04-10";
//                String six = "90";
//                String seven = "2022-05-01";
//                String eight = "80";
//                String nine = "2022-06-10";
//                String var = "ada";
//
//                GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
//                Call<CreateDataPanen> call = service.createDataPanen(token,
//                        one, two, three, four, five, six, seven, eight, nine, var);
//                call.enqueue(new Callback<CreateDataPanen>() {
//                    @Override
//                    public void onResponse(Call<CreateDataPanen> call, Response<CreateDataPanen> response) {
//                        if (response.body() != null) {
//                            Toast.makeText(MainActivity.this,
//                                    response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(MainActivity.this,
//                                    String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                        }
//                        progressDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(Call<CreateDataPanen> call, Throwable t) {
//                        Toast.makeText(MainActivity.this,
//                                t.toString(), Toast.LENGTH_LONG).show();
//                        progressDialog.dismiss();
//                    }
//                });
//
//            }
//        });

//        Button btnUpdate = findViewById(R.id.btn_update);
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setMessage("Loading....");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//
//                RequestBody commodityNameVar = createPartFromString("PADI");
//                RequestBody landCodeVar = createPartFromString("01001");
//                RequestBody periodPlantText = createPartFromString("3");
//                RequestBody harvestFlo = createPartFromString("100");
//                RequestBody harvestOnDte = createPartFromString("2022-04-10");
//                RequestBody harvestDryingflo = createPartFromString("90");
//                RequestBody harvestDryingDte = createPartFromString("2022-04-10");
//                RequestBody harvestMillingFlo = createPartFromString("90");
//                RequestBody harvestMilingDte = createPartFromString("2022-04-10");
//                RequestBody updatedByVar = createPartFromString("ada");
//
//                GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
//                Call<UpdateDataPanen> call = service.updateDataPanen(id, token, commodityNameVar,
//                        landCodeVar, periodPlantText, harvestFlo, harvestOnDte, harvestDryingflo, harvestDryingDte,
//                        harvestMillingFlo, harvestMilingDte, updatedByVar);
//                call.enqueue(new Callback<UpdateDataPanen>() {
//                    @Override
//                    public void onResponse(Call<UpdateDataPanen> call, Response<UpdateDataPanen> response) {
//                        if (response.body() != null) {
//                            Toast.makeText(MainActivity.this,
//                                    response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(MainActivity.this,
//                                    String.valueOf(response.code()), Toast.LENGTH_LONG).show();
//                        }
//                        progressDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(Call<UpdateDataPanen> call, Throwable t) {
//                        Toast.makeText(MainActivity.this,
//                                t.toString(), Toast.LENGTH_LONG).show();
//                        progressDialog.dismiss();
//                    }
//                });
//            }
//        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Call<DeleteDataPanen> call = service.deleteDataPanen(id, token);
                call.enqueue(new Callback<DeleteDataPanen>() {
                    @Override
                    public void onResponse(Call<DeleteDataPanen> call, Response<DeleteDataPanen> response) {
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this,
                                    response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<DeleteDataPanen> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                t.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
}