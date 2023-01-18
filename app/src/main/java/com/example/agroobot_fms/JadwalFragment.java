package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_one.Data;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.model.login.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalFragment extends Fragment {

    private SmartMaterialSpinner<String> spPetani;
    private SmartMaterialSpinner<String> spLahan;
    private SmartMaterialSpinner<String> spPeriode;

    LinearLayout btnCari;

    private List<String> petaniList;
    private List<String> lahanList;
    private List<String> periodeList;

    String idPetani;
    private String idLahan;
    private String idPeriode;
    private ProgressDialog progressDialog;


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

        View view = inflater.inflate(R.layout.fragment_jadwal, container,
                false);

        initSpinner(view);

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
                    getOneBody.setUserIdInt(idPetani);
                    getOneBody.setLandCodeVar(idLahan);
                    getOneBody.setPeriodPlantTxt(idPeriode);

                    SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                            Context.MODE_PRIVATE);

                    String tokenLogin = sh.getString("tokenLogin", "");

                    GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                    Call<GetOne> getOneCall = service.getOneCultivation(tokenLogin, getOneBody);
                    getOneCall.enqueue(new Callback<GetOne>() {
                        @Override
                        public void onResponse(Call<GetOne> call, Response<GetOne> response) {
                            progressDialog.dismiss();

                            if(response.code() == 200) {
                                if (response.body() != null) {
                                    String message = response.body().getMessage();

                                    if(response.body().getCode() == 0) {

                                        Data result = response.body().getData();

                                        String dogJson = new Gson().toJson(result);

                                        Intent intent = new Intent(getContext(),
                                                JadwalActivity.class);
                                        intent.putExtra("dataJadwal", dogJson);
                                        startActivity(intent);
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
                        public void onFailure(Call<GetOne> call, Throwable t) {
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

        return view;
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

    private void initSpinner(View view) {

        spPetani = view.findViewById(R.id.sp_petani);
        spLahan = view.findViewById(R.id.sp_lahan);
        spPeriode = view.findViewById(R.id.sp_periode);

        petaniList = new ArrayList<>();
        petaniList.add("10");

        lahanList = new ArrayList<>();
        lahanList.add("01001");

        periodeList = new ArrayList<>();
        periodeList.add("2201");

        spPetani.setItem(petaniList);
        spLahan.setItem(lahanList);
        spPeriode.setItem(periodeList);

        spPetani.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                idPetani = petaniList.get(position);

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

                idLahan = lahanList.get(i);

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
}