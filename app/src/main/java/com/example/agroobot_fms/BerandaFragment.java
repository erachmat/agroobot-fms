package com.example.agroobot_fms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class BerandaFragment extends Fragment {

    RelativeLayout btnLogout;
    RoundedImageView imgProfile;

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

        TextView txtUsername = view.findViewById(R.id.txt_username);
        String username = "Halo " + fullnameVar + ",";
        txtUsername.setText(username);

        PieChart chartTotalLahan = view.findViewById(R.id.chart_total_lahan);
        PieChart chartTotalLuasan = view.findViewById(R.id.chart_total_luasan);
        PieChart chartPendanaan = view.findViewById(R.id.chart_pendanaan);

        // Set the data and color to the pie chart
        chartTotalLahan.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(String.valueOf(25)),
                        Color.parseColor("#D9D9D9")));
        chartTotalLahan.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(String.valueOf(75)),
                        Color.parseColor("#508EF7")));

        // Set the data and color to the pie chart
        chartTotalLuasan.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(String.valueOf(25)),
                        Color.parseColor("#D9D9D9")));
        chartTotalLuasan.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(String.valueOf(75)),
                        Color.parseColor("#508EF7")));

        // Set the data and color to the pie chart
        chartPendanaan.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(String.valueOf(25)),
                        Color.parseColor("#D9D9D9")));
        chartPendanaan.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(String.valueOf(75)),
                        Color.parseColor("#508EF7")));

        // To animate the pie chart
        chartTotalLahan.startAnimation();
        chartTotalLuasan.startAnimation();
        chartPendanaan.startAnimation();

        counterClick = 0;

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

        return view;
    }
}