package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.agroobot_fms.model.get_all_budget_plan.Datum;
import com.example.agroobot_fms.model.get_all_budget_plan.GetAllBudgetPlan;
import com.example.agroobot_fms.model.get_one_budget_plan.BudgetDetail;
import com.example.agroobot_fms.model.get_one_budget_plan.Data;
import com.example.agroobot_fms.model.get_one_budget_plan.GetOneBudgetPlan;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    DetailAnggaranAdapter anggaranAdapter;

    private SharedPreferences sh;
    private List<BudgetDetail> listAnggaran = new ArrayList<>();
    private Comparator<BudgetDetail> comparator;

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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_menu);
        bottomNav.setItemIconTintList(null);
        bottomNav.getMenu().getItem(3).setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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
        anggaranAdapter = new DetailAnggaranAdapter(
                getApplicationContext(),
                listAnggaran,
                idSeq);
        rvAnggaran.setAdapter(anggaranAdapter);

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

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.beranda:
                Intent intent = new Intent(
                        DetailAnggaranPetaniActivity.this,
                        HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("viewpager_position", 0);
                startActivity(intent);

                finish();
                break;

            case R.id.jadwal:
                intent = new Intent(
                        DetailAnggaranPetaniActivity.this,
                        HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("viewpager_position", 1);
                startActivity(intent);

                finish();
                break;

            case R.id.panen:
                intent = new Intent(
                        DetailAnggaranPetaniActivity.this,
                        HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("viewpager_position", 2);
                startActivity(intent);

                finish();
                break;

        }
        return false;
    };

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

                            listAnggaran = dataItem.getBudgetDetail();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                comparator = (d1, d2) -> {
                                    LocalDateTime date1 = LocalDateTime.parse(d1.getCreatedOnDtm(), formatter);
                                    LocalDateTime date2 = LocalDateTime.parse(d2.getCreatedOnDtm(), formatter);
                                    return date2.compareTo(date1);
                                };
                            }

                            listAnggaran.sort(comparator);
                            anggaranAdapter.filterList(listAnggaran);

//                            anggaranAdapter = new DetailAnggaranAdapter(
//                                    getApplicationContext(),
//                                    dataItem.getBudgetDetail(),
//                                    idSeq);
//                            rvAnggaran.setAdapter(anggaranAdapter);
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

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<BudgetDetail> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (BudgetDetail item : listAnggaran) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCategoryVar().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }

        anggaranAdapter.filterList(filteredlist);

//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            dataPanenAdapter.filterList(filteredlist);
//        }
    }
}