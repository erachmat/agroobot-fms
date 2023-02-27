package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.adapter.GetActivityAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.data_dashboard.DataDashboard;
import com.example.agroobot_fms.model.data_dashboard.Datum;
import com.example.agroobot_fms.model.dropdown_farmer.DropdownFarmer;
import com.example.agroobot_fms.model.dropdown_filter_lahan.DropdownFilterLahan;
import com.example.agroobot_fms.model.dropdown_filter_periode.DropdownFilterPeriode;
import com.example.agroobot_fms.model.get_activity_dashboard.GetActivityDashboard;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.model.get_petani_dashboard.GetPetaniDashboard;
import com.makeramen.roundedimageview.RoundedImageView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaFragment extends Fragment {

    RelativeLayout btnLogout;
    RoundedImageView imgProfile;
    SwipeRefreshLayout swipeRefresh;
    TextView txtUsername, txtTotalProjects, txtTotalFa, txtTotalPetani, txtTotalPanen;
    TextView txtTotalStokBeras, txtTotalLahan, txtTotalLuasan, txtTotalPendapatan, txtBerasCampuran;
    TextView txtBeras, txtSaprodi, txtPengguna, txtRf, txtNonRf, txtSisaDana, txtTotalPendanaan;
    TextView txtRealisasiPendanaan;
    PieChart chartTotalLahan, chartTotalLuasan, chartPendanaan;
    RecyclerView rvActivity;

    LinearLayout btnCari;

    int counterClick;

    private SmartMaterialSpinner<String> spPetani;
    private SmartMaterialSpinner<String> spLahan;
    private SmartMaterialSpinner<String> spPeriode;

    private List<String> petaniList;
    private List<Integer> idPetaniList;

    private List<String> lahanList;
    private List<String> idLahanList;

    private List<String> periodeList;

    private Integer idPetani;
    private String namaPetani;

    private String idLahan;
    private String namaLahan;

    private String idPeriode;
    SharedPreferences preferences;

    private ProgressDialog progressDialog;

    public BerandaFragment() {
        // Required empty public constructor
    }


    public static BerandaFragment newInstance(String param1, String param2) {
        return new BerandaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");
        String fullnameVar = sh.getString("fullnameVar", "");
        String idSeq = sh.getString("idSeq", "");

        initView(view, fullnameVar, tokenLogin, idSeq);
        initSpinner(view, tokenLogin);
        initData(tokenLogin, idSeq);

        counterClick = 0;

        return view;
    }

    private void initSpinner(View view, String tokenLogin) {

        spPetani = view.findViewById(R.id.sp_petani);
        spLahan = view.findViewById(R.id.sp_lahan);
        spPeriode = view.findViewById(R.id.sp_periode);

        setSpinnerPetani(tokenLogin);

        spPetani.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                idPetani = idPetaniList.get(position);

                idLahanList = new ArrayList<>();
                idLahan = "";
                spLahan.setItem(idLahanList);

                periodeList = new ArrayList<>();
                idPeriode = "";
                spPeriode.setItem(periodeList);

                setSpinnerLahan(tokenLogin, idPetani);

//                Toast.makeText(getActivity(), idPetani,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spLahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {

                idLahan = idLahanList.get(i);
                setSpinnerPeriode(tokenLogin, idLahan);

//                Toast.makeText(getActivity(), idLahan,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {

                idPeriode = periodeList.get(i);

//                Toast.makeText(getActivity(), idPeriode,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setSpinnerPeriode(String tokenLogin, String idLahan) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFilterPeriode> dropdownFilterPeriodeCall = service.dropdownFilterPeriode(tokenLogin,
                idLahan);
        dropdownFilterPeriodeCall.enqueue(new Callback<DropdownFilterPeriode>() {
            @Override
            public void onResponse(Call<DropdownFilterPeriode> call,
                                   Response<DropdownFilterPeriode> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_filter_periode.Datum>
                                    listData = response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            periodeList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                periodeList.add(listData.get(i).getPeriodPlantTxt());
                            }

                            spPeriode.setItem(periodeList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_LAHAN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFilterPeriode> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerLahan(String tokenLogin, Integer idPetani) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFilterLahan> dropdownFilterLahanCall = service.dropdownFilterLahan(tokenLogin,
                idPetani);
        dropdownFilterLahanCall.enqueue(new Callback<DropdownFilterLahan>() {
            @Override
            public void onResponse(Call<DropdownFilterLahan> call,
                                   Response<DropdownFilterLahan> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_filter_lahan.Datum> listData =
                                    response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            lahanList = new ArrayList<>();
                            idLahanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                lahanList.add(listData.get(i).getLandNameVar());
                                idLahanList.add(listData.get(i).getLandCodeVar());
                            }

                            spLahan.setItem(lahanList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_LAHAN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFilterLahan> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerPetani(String tokenLogin) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFarmer> dropdownFarmerCall = service.dropdownFarmer(tokenLogin);
        dropdownFarmerCall.enqueue(new Callback<DropdownFarmer>() {
            @Override
            public void onResponse(Call<DropdownFarmer> call,
                                   Response<DropdownFarmer> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {
                            List<com.example.agroobot_fms.model.dropdown_farmer.Datum> listData = response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            petaniList = new ArrayList<>();
                            idPetaniList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                petaniList.add(listData.get(i).getFullnameVar());
                                idPetaniList.add(listData.get(i).getIdSeq());
                            }

                            spPetani.setItem(petaniList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_PETANI", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFarmer> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void initData(String tokenLogin, String idSeq) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DataDashboard> dataDashboardCall = service.getDataDashboard(tokenLogin);
        dataDashboardCall.enqueue(new Callback<DataDashboard>() {
            @Override
            public void onResponse(Call<DataDashboard> call, Response<DataDashboard> response) {

                swipeRefresh.setRefreshing(false);

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            Datum dataItem = response.body().getData().get(0);

                            txtTotalProjects.setText(String.valueOf(dataItem.getTotalProjectInt()));
                            txtTotalFa.setText(String.valueOf(dataItem.getTotalFaInt()));
//                            txtTotalPetani.setText(String.valueOf(dataItem.getTotalFarmerInt()));

                            String totalPanen = dataItem.getTotalPanenFlo() + " Kg";
                            txtTotalPanen.setText(totalPanen);

                            String totalStokBeras = dataItem.getStockBerasInt() + " Kg";
                            txtTotalStokBeras.setText(totalStokBeras);

                            txtTotalLahan.setText(String.valueOf(dataItem.getTotalLandInt()));

                            String totalLuasan = dataItem.getTotalAreaFlo() + " m2";
                            txtTotalLuasan.setText(totalLuasan);

                            // Set the data and color to the pie chart
                            chartTotalLahan.addPieSlice(
                                    new PieModel(
                                            "Lahan Non Aktif",
                                            Integer.parseInt(String.valueOf(0)),
                                            Color.parseColor("#D9D9D9")));
                            chartTotalLahan.addPieSlice(
                                    new PieModel(
                                            "Lahan Aktif",
                                            Integer.parseInt(String.valueOf(dataItem
                                                    .getTotalLandActiveInt())),
                                            Color.parseColor("#508EF7")));

                            // Set the data and color to the pie chart
                            chartTotalLuasan.addPieSlice(
                                    new PieModel(
                                            "Luas Non Aktif",
                                            Integer.parseInt(String.valueOf(0)),
                                            Color.parseColor("#D9D9D9")));
                            chartTotalLuasan.addPieSlice(
                                    new PieModel(
                                            "Luas Aktif",
                                            Float.parseFloat(String.valueOf(dataItem
                                                    .getTotalAreaActiveFlo())),
                                            Color.parseColor("#508EF7")));

                            chartPendanaan.clearChart();

                            // Set the data and color to the pie chart
                            chartPendanaan.addPieSlice(
                                    new PieModel(
                                            "Non RF",
                                            Integer.parseInt(String.valueOf(dataItem
                                                    .getTotalNonrfInt())),
                                            Color.parseColor("#D9D9D9")));
                            chartPendanaan.addPieSlice(
                                    new PieModel(
                                            "RF",
                                            Integer.parseInt(String.valueOf(dataItem
                                                    .getTotalRfInt())),
                                            Color.parseColor("#508EF7")));

                            // To animate the pie chart
                            chartTotalLahan.startAnimation();
                            chartTotalLuasan.startAnimation();
                            chartPendanaan.startAnimation();

                            if(dataItem.getTotalGrossRevenueInt() == null) {
                                txtTotalPendapatan.setText("Rp 0");
                            } else {
                                String totalPendapatan = NumberFormat.getInstance(Locale.ENGLISH).
                                        format(dataItem.getTotalGrossRevenueInt());
                                totalPendapatan = "Rp " + totalPendapatan;
                                txtTotalPendapatan.setText(totalPendapatan);
                            }

                            String berasCampuran = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(dataItem.getTotalGrossBerasCampuranInt());
                            berasCampuran = "Rp " + berasCampuran;
                            txtBerasCampuran.setText(berasCampuran);

                            String beras = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(dataItem.getTotalGrossBerasInt());
                            beras = "Rp " + beras;
                            txtBeras.setText(beras);

                            String saprodi = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(dataItem.getTotalGrossSaprodiInt());
                            saprodi = "Rp " + saprodi;
                            txtSaprodi.setText(saprodi);

                            int totalPengguna = dataItem.getTotalRfInt()
                                    + dataItem.getTotalNonrfInt();
                            txtPengguna.setText(String.valueOf(totalPengguna));

                            txtRf.setText(String.valueOf(dataItem.getTotalRfInt()));

                            txtNonRf.setText(String.valueOf(dataItem.getTotalNonrfInt()));

                            int sisaDana = dataItem.getTotalFundingInt() - dataItem
                                    .getTotalRealisasiFundingInt();
                            String danaSisa = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(sisaDana);
                            danaSisa = "Rp " + danaSisa;
                            txtSisaDana.setText(danaSisa);

                            String totalPendanaan = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(dataItem.getTotalFundingInt());
                            totalPendanaan = "Rp " + totalPendanaan;
                            txtTotalPendanaan.setText(totalPendanaan);

                            String realisasiPendanaan = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(dataItem.getTotalRealisasiFundingInt());
                            realisasiPendanaan = "Rp " + realisasiPendanaan;
                            txtRealisasiPendanaan.setText(realisasiPendanaan);

                            getTotalPetani(tokenLogin, idSeq);

                        }
                        else {

                            SharedPreferences sh = getContext().getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(getContext(),
                                    LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(getContext(), message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataDashboard> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void getTotalPetani(String tokenLogin, String idSeq) {
        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetPetaniDashboard> getPetaniDashboardCall = service.getPetaniDashboard(tokenLogin, idSeq);
        getPetaniDashboardCall.enqueue(new Callback<GetPetaniDashboard>() {
            @Override
            public void onResponse(Call<GetPetaniDashboard> call, Response<GetPetaniDashboard> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.get_petani_dashboard.Datum> dataItem
                                    = response.body().getData();
                            txtTotalPetani.setText(String.valueOf(dataItem.get(0).getTotalFarmerInt()));
                        }
                        else {

                            SharedPreferences sh = getContext().getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(getContext(),
                                    LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        }

//                        String message = response.body().getMessage();
//                        Toast.makeText(getContext(), message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPetaniDashboard> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }

    private void initView(View view, String fullnameVar, String tokenLogin, String idSeq) {

        txtUsername = view.findViewById(R.id.txt_username);
        String username = "Halo " + fullnameVar + ",";
        txtUsername.setText(username);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM YYYY");
        String date = dateFormat.format(calendar.getTime());

        TextView txtTodayDate = view.findViewById(R.id.txt_today_date);
        txtTodayDate.setText(date);

        imgProfile = view.findViewById(R.id.img_profile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counterClick ++;

                if(counterClick % 2 == 0) {
                    btnLogout.setVisibility(View.GONE);
                } else {
                    btnLogout.setVisibility(View.VISIBLE);
                }

            }
        });

        rvActivity = view.findViewById(R.id.rv_activity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActivity.setLayoutManager(layoutManager);

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(tokenLogin, idSeq);
            }
        });

        txtTotalProjects = view.findViewById(R.id.txt_total_projects);
        txtTotalFa = view.findViewById(R.id.txt_total_fa);
        txtTotalPetani = view.findViewById(R.id.txt_total_petani);
        txtTotalPanen = view.findViewById(R.id.txt_total_panen);
        txtTotalStokBeras = view.findViewById(R.id.txt_total_stock_beras);
        txtTotalLahan = view.findViewById(R.id.txt_total_lahan);
        txtTotalLuasan = view.findViewById(R.id.txt_total_luasan);
        txtTotalPendapatan = view.findViewById(R.id.txt_total_pendapatan);
        txtBerasCampuran = view.findViewById(R.id.txt_beras_campuran);
        txtBeras = view.findViewById(R.id.txt_beras);
        txtSaprodi = view.findViewById(R.id.txt_saprodi);
        txtPengguna = view.findViewById(R.id.txt_pengguna);
        txtRf = view.findViewById(R.id.txt_rf);
        txtNonRf = view.findViewById(R.id.txt_non_rf);
        txtSisaDana = view.findViewById(R.id.txt_sisa_dana);
        txtTotalPendanaan = view.findViewById(R.id.txt_total_pendanaan);
        txtRealisasiPendanaan = view.findViewById(R.id.txt_realisasi_pendanaan);

        chartTotalLahan = view.findViewById(R.id.chart_total_lahan);
        chartTotalLuasan = view.findViewById(R.id.chart_total_luasan);
        chartPendanaan = view.findViewById(R.id.chart_pendanaan);

        btnCari = view.findViewById(R.id.btn_cari_jadwal);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllField()) {

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    GetOneBody getOneBody = new GetOneBody();
                    getOneBody.setUserIdInt(String.valueOf(idPetani));
                    getOneBody.setLandCodeVar(idLahan);
                    getOneBody.setPeriodPlantTxt(idPeriode);

                    SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                            Context.MODE_PRIVATE);

                    String tokenLogin = sh.getString("tokenLogin", "");

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<GetActivityDashboard> getOneCall = service.getActivityDashboard(tokenLogin, idPetani,
                            idLahan, idPeriode);
                    getOneCall.enqueue(new Callback<GetActivityDashboard>() {
                        @Override
                        public void onResponse(Call<GetActivityDashboard> call,
                                               Response<GetActivityDashboard> response) {
                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {

                                    String message = response.body().getMessage();
                                    if(response.body().getCode() == 0) {

                                        preferences = getContext().getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("idPetani", String.valueOf(idPetani));
                                        editor.putString("idLahan", idLahan);
                                        editor.putString("idPeriode", idPeriode);
                                        editor.apply();

                                        List<com.example.agroobot_fms.model.get_activity_dashboard.Datum>
                                                listData = response.body().getData();

                                        GetActivityAdapter getActivityAdapter =
                                                new GetActivityAdapter(getContext(),
                                                        listData);
                                        rvActivity.setAdapter(getActivityAdapter);

                                    } else {

                                        preferences = getContext().getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(getContext(),
                                                LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                    Toast.makeText(getContext(), message,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(),
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(),
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetActivityDashboard> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });
                }
            }
        });

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getContext().getSharedPreferences("MySharedPref",
                        Context.MODE_PRIVATE);
                settings.edit().clear().apply();

                Intent intent = new Intent(getContext(),
                        LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private boolean checkAllField() {

        if(idPetani == null || idPetani.equals("")) {
            Toast.makeText(getActivity(), "Pilih petani terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idLahan == null || idLahan.equals("")) {
            Toast.makeText(getActivity(), "Pilih lahan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idPeriode == null || idPeriode.equals("")) {
            Toast.makeText(getActivity(), "Pilih periode terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // after all validation return true.
        return true;
    }
}