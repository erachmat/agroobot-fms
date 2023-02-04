package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Calendar;

public class EditSaranActivity extends AppCompatActivity {

    private SharedPreferences sh;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_saran);

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");
        String idPetani = sh.getString("idPetani", "");
        String idLahan = sh.getString("idLahan", "");
        String idPeriode = sh.getString("idPeriode", "");
        String fullnameVar = sh.getString("fullnameVar", "");

        calendar = Calendar.getInstance();

        initView(tokenLogin, idPetani, idLahan, idPeriode, fullnameVar);
        initSpinner();
    }

    private void initSpinner() {

    }

    private void initView(String tokenLogin, String idPetani, String idLahan,
                          String idPeriode, String fullnameVar) {


    }
}