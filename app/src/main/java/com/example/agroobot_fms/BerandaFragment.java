package com.example.agroobot_fms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.adapter.DataPanenAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.data_dashboard.DataDashboard;
import com.example.agroobot_fms.model.data_dashboard.Datum;
import com.example.agroobot_fms.model.get_all_data_panen.DataPanen;
import com.makeramen.roundedimageview.RoundedImageView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
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

    int counterClick;

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

        initView(view, fullnameVar);
        initData(tokenLogin);

        counterClick = 0;

        return view;
    }

    private void initData(String tokenLogin) {

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
                            txtTotalPetani.setText(String.valueOf(dataItem.getTotalFarmerInt()));

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

                        } else {

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

    private void initView(View view, String fullnameVar) {

        txtUsername = view.findViewById(R.id.txt_username);
        String username = "Halo " + fullnameVar + ",";
        txtUsername.setText(username);

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

        swipeRefresh = view.findViewById(R.id.swipe_refresh);

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
}