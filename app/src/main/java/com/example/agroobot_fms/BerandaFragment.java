package com.example.agroobot_fms;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class BerandaFragment extends Fragment {

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
        String username = "Hallo " + fullnameVar + ",";
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

        return view;
    }
}