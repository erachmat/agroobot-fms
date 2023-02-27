package com.example.agroobot_fms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.login.Data;
import com.example.agroobot_fms.model.login.LoginBody;
import com.example.agroobot_fms.model.login.LoginResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    ImageView showPassBtn;
    TextView txtForgetPass, txtRegister;
    LinearLayout btnLogin, btnLoginGoogle;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

//        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.remove("isUserLogin");
//        editor.apply();

        showPassBtn.setOnClickListener(view -> ShowHidePass(showPassBtn));

        btnLogin.setOnClickListener(view -> {
            if (checkAllFields()) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String username = etUsername.getText().
                        toString().trim();

                String password = etPassword.getText().
                        toString().trim();

                LoginBody loginBody = new LoginBody();
                loginBody.setPhoneNumberInt(username);
                loginBody.setPasswordVar(password);

                GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                Call<LoginResponse> loginResponseCall = service.loginAccount(loginBody);
                loginResponseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call,
                                           @NonNull Response<LoginResponse> response) {

                        progressDialog.dismiss();

                        if(response.code() == 200) {
                            if (response.body() != null) {

                                String message = response.body().getMessage();
                                if(response.body().getCode() == 0) {

                                    Data result = response.body().getData();

                                    SharedPreferences preferences = getSharedPreferences(
                                            "MySharedPref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("tokenLogin", result.getToken());
                                    editor.putString("fullnameVar", result.getFullnameVar());
                                    editor.putString("idSeq", String.valueOf(result.getIdSeq()));
                                    editor.putBoolean("isUserLogin", true);
                                    editor.apply();

                                    startActivity(new Intent(LoginActivity.this,
                                            HomeActivity.class));
                                    finish();
                                }

                                Toast.makeText(LoginActivity.this, message,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call,
                                          @NonNull Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(LoginActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                        Log.e("Failure", t.toString());
                    }
                });
            }
        });

    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        showPassBtn = findViewById(R.id.show_pass_btn);
        txtForgetPass = findViewById(R.id.txt_forget_pass);
        btnLogin = findViewById(R.id.btn_login);
    }

    public void ShowHidePass(View view){
        if(view.getId() == R.id.show_pass_btn) {
            if(etPassword.getTransformationMethod().equals(
                    PasswordTransformationMethod.getInstance())){
                //Show Password
                etPassword.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance());
            }
            else{
                //Hide Password
                etPassword.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
            }
        }
    }

    public boolean checkAllFields() {

        if (etUsername.length() == 0) {
            etUsername.setError("This field is required");
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        } else if (etPassword.length() < 5) {
            etPassword.setError("Password must be minimum 5 characters");
            return false;
        }

        // after all validation return true.
        return true;
    }
}