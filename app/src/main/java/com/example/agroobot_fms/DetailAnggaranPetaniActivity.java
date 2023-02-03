package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.adapter.AnggaranAdapter;
import com.example.agroobot_fms.adapter.DetailAnggaranAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_all_budget_plan.GetAllBudgetPlan;
import com.example.agroobot_fms.model.get_one_budget_plan.Data;
import com.example.agroobot_fms.model.get_one_budget_plan.GetOneBudgetPlan;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnggaranPetaniActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    EditText etSearch;
    RecyclerView rvAnggaran;
    ImageView btnAdd, btnBack;
    TextView txtNamaPetani, txtKodeLahan, txtTotalBudget;

    private SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anggaran_petani);

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");

            initView(idSeq);
            initDataAnggaran(idSeq);
        }
    }

    private void initView(String idSeq) {

        swipeRefresh = findViewById(R.id.swipe_refresh);
        etSearch = findViewById(R.id.et_search);
        rvAnggaran = findViewById(R.id.rv_anggaran);
        btnAdd = findViewById(R.id.btn_add);
        txtNamaPetani = findViewById(R.id.txt_nama_petani);
        txtKodeLahan = findViewById(R.id.txt_kode_lahan);
        txtTotalBudget = findViewById(R.id.txt_total_budget);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAnggaran.setLayoutManager(layoutManager);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataAnggaran(idSeq);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAnggaranPetaniActivity.this,
                        AddDataAnggaranActivty.class);
                intent.putExtra("idSeq", idSeq);
                startActivity(intent);
            }
        });
    }

    private void initDataAnggaran(String idSeq) {

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetOneBudgetPlan> getOneBudgetPlanCall = service.getOneBudgetPlan(tokenLogin,
                Integer.parseInt(idSeq));
        getOneBudgetPlanCall.enqueue(new Callback<GetOneBudgetPlan>() {
            @Override
            public void onResponse(Call<GetOneBudgetPlan> call,
                                   Response<GetOneBudgetPlan> response) {

                swipeRefresh.setRefreshing(false);

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            Log.e("TEST OUTPUT", response.body().toString());

                            Data dataItem = response.body().getData();

                            txtNamaPetani.setText(dataItem.getBudgetPlan()
                                    .get(0).getFullnameVar());

                            String kodeLahan = "[" + dataItem.getBudgetPlan()
                                    .get(0).getLandCodeVar() + "] " + dataItem.getBudgetPlan()
                                    .get(0).getLandNameVar();
                            txtKodeLahan.setText(kodeLahan);

                            String hargaRp = "IDR " + NumberFormat.getInstance(Locale.ENGLISH).
                                    format(Integer.parseInt(dataItem.getBudgetPlan().get(0)
                                            .getBudgetPlanVar()));
                            txtTotalBudget.setText(hargaRp);

                            DetailAnggaranAdapter anggaranAdapter = new DetailAnggaranAdapter(
                                    getApplicationContext(),
                                    dataItem.getBudgetDetail(),
                                    idSeq);
                            rvAnggaran.setAdapter(anggaranAdapter);
                        } else {

                            sh = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    DetailAnggaranPetaniActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(DetailAnggaranPetaniActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailAnggaranPetaniActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailAnggaranPetaniActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneBudgetPlan> call, Throwable t) {

                swipeRefresh.setRefreshing(false);

//                progressDialog.dismiss();
                Toast.makeText(DetailAnggaranPetaniActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }
}