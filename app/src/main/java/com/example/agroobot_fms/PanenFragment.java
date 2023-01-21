package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    SwipeRefreshLayout swipeRefresh;
    private ProgressDialog progressDialog;

    SharedPreferences sh;

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

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        etSearch = view.findViewById(R.id.et_search);
        rvDataPanen = view.findViewById(R.id.rv_data_panen);
        btnAdd = view.findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        AddDataPanenActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvDataPanen.setLayoutManager(layoutManager);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataPanen();
            }
        });
    }

    private void initDataPanen() {

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading....");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        sh = getContext().getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DataPanen> dataPanenCall = service.getAllDataPanen(tokenLogin);
        dataPanenCall.enqueue(new Callback<DataPanen>() {
            @Override
            public void onResponse(Call<DataPanen> call, Response<DataPanen> response) {

//                progressDialog.dismiss();

                swipeRefresh.setRefreshing(false);

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {
                            DataPanenAdapter dataPanenAdapter = new DataPanenAdapter(getContext(),
                                    response.body().getData());
                            rvDataPanen.setAdapter(dataPanenAdapter);
                        } else {

                            sh = getContext().getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(getContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        String message = response.body().getMessage();
                        Toast.makeText(getContext(), message,
                                Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<DataPanen> call, Throwable t) {
//                progressDialog.dismiss();

                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }
}