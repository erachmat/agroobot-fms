package com.example.agroobot_fms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnggaranFragment extends Fragment {

      public AnggaranFragment() {
        // Required empty public constructor
    }

    public static AnggaranFragment newInstance(String param1, String param2) {
        return new AnggaranFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anggaran, container, false);


        return view;
    }
}