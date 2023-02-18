package com.example.agroobot_fms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.example.agroobot_fms.model.dropdown_farmer.Datum;
import com.example.agroobot_fms.model.dropdown_farmer.DropdownFarmer;
import com.example.agroobot_fms.model.dropdown_filter_lahan.DropdownFilterLahan;
import com.example.agroobot_fms.model.dropdown_filter_periode.DropdownFilterPeriode;
import com.example.agroobot_fms.model.get_one.Data;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.google.gson.Gson;

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
    private List<Integer> idPetaniList;

    private List<String> lahanList;
    private List<String> idLahanList;
    private List<String> periodeList;

    private Integer idPetani;
    private String namaPetani;

    private String idLahan;
    private String namaLahan;

    private String idPeriode;
    SharedPreferences preferences;

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

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        initSpinner(view, tokenLogin);

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
                    getOneBody.setUserIdInt(String.valueOf(idPetani));
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

                                        preferences = getContext().getSharedPreferences(
                                        "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("idPetani", String.valueOf(idPetani));
                                        editor.putString("idLahan", idLahan);
                                        editor.putString("idPeriode", idPeriode);
                                        editor.apply();

                                        Data result = response.body().getData();

                                        String dataJson = new Gson().toJson(result);

                                        Intent intent = new Intent(getContext(),
                                                JadwalActivity.class);
                                        intent.putExtra("dataJadwal", dataJson);
                                        startActivity(intent);
                                    } else {

                                        preferences = getContext().getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(getContext(),
                                                LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
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

    private void initSpinner(View view, String tokenLogin) {

        spPetani = view.findViewById(R.id.sp_petani);
        spLahan = view.findViewById(R.id.sp_lahan);
        spPeriode = view.findViewById(R.id.sp_periode);

        setSpinnerPetani(tokenLogin);

        spPetani.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                idPetani = idPetaniList.get(position);

                idLahanList = new ArrayList<>();
                idLahan = "";
                spLahan.setItem(idLahanList);

                periodeList = new ArrayList<>();
                idPeriode = "";
                spPeriode.setItem(periodeList);

                setSpinnerLahan(tokenLogin, idPetani);

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

                idLahan = idLahanList.get(i);
                setSpinnerPeriode(tokenLogin, idLahan);

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

    private void setSpinnerPeriode(String tokenLogin, String idLahan) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFilterPeriode> dropdownFilterPeriodeCall = service.dropdownFilterPeriode(tokenLogin,
                idLahan);
        dropdownFilterPeriodeCall.enqueue(new Callback<DropdownFilterPeriode>() {
            @Override
            public void onResponse(Call<DropdownFilterPeriode> call,
                                   Response<DropdownFilterPeriode> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_filter_periode.Datum>
                                    listData = response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            periodeList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                periodeList.add(listData.get(i).getPeriodPlantTxt());
                            }

                            spPeriode.setItem(periodeList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_LAHAN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
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
            public void onFailure(Call<DropdownFilterPeriode> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerLahan(String tokenLogin, Integer idPetani) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFilterLahan> dropdownFilterLahanCall = service.dropdownFilterLahan(tokenLogin,
                idPetani);
        dropdownFilterLahanCall.enqueue(new Callback<DropdownFilterLahan>() {
            @Override
            public void onResponse(Call<DropdownFilterLahan> call,
                                   Response<DropdownFilterLahan> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            List<com.example.agroobot_fms.model.dropdown_filter_lahan.Datum> listData =
                                    response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            lahanList = new ArrayList<>();
                            idLahanList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                lahanList.add(listData.get(i).getLandNameVar());
                                idLahanList.add(listData.get(i).getLandCodeVar());
                            }

                            spLahan.setItem(lahanList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_LAHAN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
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
            public void onFailure(Call<DropdownFilterLahan> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerPetani(String tokenLogin) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFarmer> dropdownFarmerCall = service.dropdownFarmer(tokenLogin);
        dropdownFarmerCall.enqueue(new Callback<DropdownFarmer>() {
            @Override
            public void onResponse(Call<DropdownFarmer> call,
                                   Response<DropdownFarmer> response) {
                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {
                            List<Datum> listData = response.body().getData();

//                            Toast.makeText(EditPengamatanActivity.this,
//                                    String.valueOf(listData.size()),
//                                    Toast.LENGTH_SHORT).show();

                            petaniList = new ArrayList<>();
                            idPetaniList = new ArrayList<>();

                            for(int i = 0; i < listData.size(); i++) {
                                petaniList.add(listData.get(i).getFullnameVar());
                                idPetaniList.add(listData.get(i).getIdSeq());
                            }

                            spPetani.setItem(petaniList);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_PETANI", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
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
            public void onFailure(Call<DropdownFarmer> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }
}