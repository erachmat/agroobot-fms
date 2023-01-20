package com.example.agroobot_fms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.agroobot_fms.adapter.ActivityAdapter;
import com.example.agroobot_fms.adapter.DataPanenAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.create_observation.CreateObservation;
import com.example.agroobot_fms.model.get_all_data_panen.DataPanen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanenFragment extends Fragment {

    EditText etSearch;
    ImageView btnAdd;
    RecyclerView rvDataPanen;

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

        View view = inflater.inflate(R.layout.fragment_panen, container, false);

        initView(view);
        initDataPanen();

        return view;
    }

    private void initView(View view) {
        etSearch = view.findViewById(R.id.et_search);

        btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        AddDataPanenActivity.class);
                startActivity(intent);
            }
        });

        rvDataPanen = view.findViewById(R.id.rv_data_panen);
    }

    private void initDataPanen() {

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DataPanen> dataPanenCall = service.getAllDataPanen(tokenLogin);
        dataPanenCall.enqueue(new Callback<DataPanen>() {
            @Override
            public void onResponse(Call<DataPanen> call, Response<DataPanen> response) {

            }

            @Override
            public void onFailure(Call<DataPanen> call, Throwable t) {

            }
        });

//        DataPanenAdapter dataPanenAdapter = new ActivityAdapter(getContext(),
//                data.getActivity());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        rvDataPanen.setLayoutManager(layoutManager);
//        rvDataPanen.setAdapter(dataPanenAdapter);
    }
}