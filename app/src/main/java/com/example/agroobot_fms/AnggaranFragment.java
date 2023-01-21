package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agroobot_fms.adapter.AnggaranAdapter;
import com.example.agroobot_fms.adapter.DataPanenAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_all_budget_plan.GetAllBudgetPlan;
import com.example.agroobot_fms.model.get_all_data_panen.DataPanen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnggaranFragment extends Fragment {

    EditText etSearch;
    RecyclerView rvAnggaran;
    private ProgressDialog progressDialog;

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

        initView(view);
        initDataAnggaran();

        return view;
    }

    private void initView(View view) {

        etSearch = view.findViewById(R.id.et_search);

        rvAnggaran = view.findViewById(R.id.rv_anggaran);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAnggaran.setLayoutManager(layoutManager);

    }

    private void initDataAnggaran() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetAllBudgetPlan> getAllBudgetPlanCall = service.getAllBudgetPlan(tokenLogin);
        getAllBudgetPlanCall.enqueue(new Callback<GetAllBudgetPlan>() {
            @Override
            public void onResponse(Call<GetAllBudgetPlan> call,
                                   Response<GetAllBudgetPlan> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        String message = response.body().getMessage();

                        if(response.body().getCode() == 0) {
                            AnggaranAdapter anggaranAdapter = new AnggaranAdapter(getContext(),
                                    response.body().getData());
                            rvAnggaran.setAdapter(anggaranAdapter);
                        }

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
            public void onFailure(Call<GetAllBudgetPlan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }
}