package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailDataPanenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_panen);

        initView();

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");

            initData(idSeq);
        }
    }

    private void initView() {

    }

    private void initData(String idSeq) {

    }
}