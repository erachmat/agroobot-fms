package com.example.agroobot_fms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PanenFragment extends Fragment {

    public PanenFragment() {
        // Required empty public constructor
    }


    public static PanenFragment newInstance(String param1, String param2) {
        return new PanenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_panen, container, false);


        return view;
    }
}