package com.example.agroobot_fms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class JadwalFragment extends Fragment {

    private SmartMaterialSpinner<String> spProvince;
    private SmartMaterialSpinner<String> spEmptyItem;
    private List<String> provinceList;

    public JadwalFragment() {
        // Required empty public constructor
    }

    public static JadwalFragment newInstance(String param1, String param2) {
        return new JadwalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);

        initSpinner(view);

        return view;
    }

    private void initSpinner(View view) {
        spProvince = view.findViewById(R.id.sp_petani);
//        spEmptyItem = view.findViewById(R.id.sp_empty_item);
        provinceList = new ArrayList<>();

        provinceList.add("Kampong Thom");
        provinceList.add("Kampong Cham");
        provinceList.add("Kampong Chhnang");
        provinceList.add("Phnom Penh");
        provinceList.add("Kandal");
        provinceList.add("Kampot");

        spProvince.setItem(provinceList);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), provinceList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}