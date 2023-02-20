package com.example.agroobot_fms;

import static com.example.agroobot_fms.utils.CalendarUtils.daysInWeekArray;
import static com.example.agroobot_fms.utils.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.agroobot_fms.adapter.ActivityAdapter;
import com.example.agroobot_fms.adapter.CalendarAdapter;
import com.example.agroobot_fms.adapter.CatatanAdapter;
import com.example.agroobot_fms.adapter.DokumentasiAdapter;
import com.example.agroobot_fms.adapter.EventAdapter;
import com.example.agroobot_fms.adapter.PengamatanAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.Event;
import com.example.agroobot_fms.model.dropdown_farmer.Datum;
import com.example.agroobot_fms.model.dropdown_farmer.DropdownFarmer;
import com.example.agroobot_fms.model.dropdown_filter_lahan.DropdownFilterLahan;
import com.example.agroobot_fms.model.dropdown_filter_periode.DropdownFilterPeriode;
import com.example.agroobot_fms.model.get_one.Activity;
import com.example.agroobot_fms.model.get_one.Data;
import com.example.agroobot_fms.model.get_one.Documentation;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.model.get_one.Observation;
import com.example.agroobot_fms.model.get_one.Rating;
import com.example.agroobot_fms.utils.CalendarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private LocalDate selectedDate;
    private JsonObject dataJadwal;

    private RecyclerView calendarRecyclerView, rvActivity, rvPengamatan;
    private RecyclerView rvDokumentasi, rvCatatan;
//    private RelativeLayout btnAdd;
    RoundedImageView btnAdd;
    CardView lytRvDokumentasi;
    private TextView monthYearText;
    TextView btnActivity, btnPengamatan, btnDokumentasi, btnCatatan;
    TextView txtTanggal, txtHari, txtBulan;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout lytTodayDate;
    ImageView btnBack;
    private SmartMaterialSpinner<String> spPetani;
    private SmartMaterialSpinner<String> spLahan;
    private SmartMaterialSpinner<String> spPeriode;
    LinearLayout btnCari;

    Data data;

    ActivityAdapter activityAdapter;
    PengamatanAdapter pengamatanAdapter;
    DokumentasiAdapter dokumentasiAdapter;
    CatatanAdapter catatanAdapter;

    private List<Activity> listActivity = new ArrayList<>();
    private List<Observation> listPengamatan = new ArrayList<>();
    private List<Documentation> listDokumentasi = new ArrayList<>();
    private List<Rating> listRating = new ArrayList<>();

    private List<String> petaniList;
    private List<String> idPetaniList;

    private List<String> lahanList;
    private List<String> idLahanList;
    private List<String> periodeList;

    private String idPetaniSpinner;
    private String namaPetani;

    private String idLahanSpinner;
    private String namaLahan;

    private String idPeriodeSpinner;
    SharedPreferences preferences;
    private TextView txtNamaPetani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        SharedPreferences sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        String idPetani = sh.getString("idPetani", "");
        namaPetani = sh.getString("namaPetani", "");

        Set<String> petaniListSet = sh.getStringSet("petaniList", new HashSet<>());
        petaniList = new ArrayList<>(petaniListSet);

        Set<String> idPetaniListSet = sh.getStringSet("idPetaniList", new HashSet<>());
        idPetaniList = new ArrayList<>(idPetaniListSet);

        String idLahan = sh.getString("idLahan", "");
        namaLahan = sh.getString("namaLahan", "");

        Set<String> lahanListSet = sh.getStringSet("lahanList", new HashSet<>());
        lahanList = new ArrayList<>(lahanListSet);

        Set<String> idLahanListSet = sh.getStringSet("idLahanList", new HashSet<>());
        idLahanList = new ArrayList<>(idLahanListSet);

        String idPeriode = sh.getString("idPeriode", "");

        Set<String> periodeListSet = sh.getStringSet("periodeList", new HashSet<>());
        periodeList = new ArrayList<>(periodeListSet);


        if (getIntent().getExtras() != null) {

            initWidgets(tokenLogin, idPetani,
                    idLahan, idPeriode);

            String dataJson = getIntent().getStringExtra("dataJadwal");

//            namaPetani = getIntent().getStringExtra("namaPetani");
//            namaLahan = getIntent().getStringExtra("namaLahan");
//            idPeriode = getIntent().getStringExtra("namaPeriode");

//            petaniList = getIntent().getStringArrayListExtra("petaniList");
//            idPetaniList = getIntent().getIntegerArrayListExtra("idPetaniList");
//            lahanList = getIntent().getStringArrayListExtra("lahanList");
//            idLahanList = getIntent().getStringArrayListExtra("idLahanList");
//            periodeList = getIntent().getStringArrayListExtra("periodeList");

            if(dataJson != null) {

                Gson gson = new Gson();
                data = gson.fromJson(dataJson, Data.class);

                setRvActivity(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvPengamatan(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvCatatan(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvDokumentasi(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                initSpinner(tokenLogin, idPetani,
                        idLahan, idPeriode);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CalendarUtils.selectedDate = LocalDate.now();
                    filter(CalendarUtils.selectedDate.toString());
                }

//                Toast.makeText(this, data.JadwalActivity.this.get(0).getActivityTxt(),
//                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            initWidgets(tokenLogin, idPetani,
                    idLahan, idPeriode);
            initSpinner(tokenLogin, idPetani,
                    idLahan, idPeriode);
            refreshData(tokenLogin, idPetani, idLahan, idPeriode);
        }

        setWeekView();
    }

    private void refreshData(String tokenLogin, String idPetani,
                             String idLahan, String idPeriode) {

        GetOneBody getOneBody = new GetOneBody();
        getOneBody.setUserIdInt(idPetani);
        getOneBody.setLandCodeVar(idLahan);
        getOneBody.setPeriodPlantTxt(idPeriode);

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetOne> getOneCall = service.getOneCultivation(tokenLogin, getOneBody);
        getOneCall.enqueue(new Callback<GetOne>() {
            @Override
            public void onResponse(Call<GetOne> call, Response<GetOne> response) {

                swipeRefresh.setRefreshing(false);

                if(response.code() == 200) {
                    if (response.body() != null) {

                        SharedPreferences preferences;

                        if(response.body().getCode() == 0) {

                            preferences = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("idPetani", idPetani);
                            editor.putString("idLahan", idLahan);
                            editor.putString("idPeriode", idPeriode);
                            editor.apply();

                            data = response.body().getData();

                            setRvActivity(getApplicationContext(), data,
                                    tokenLogin, idPetani,
                                    idLahan, idPeriode);

                            setRvPengamatan(getApplicationContext(), data,
                                    tokenLogin, idPetani, idLahan, idPeriode);

                            setRvCatatan(getApplicationContext(), data,
                                    tokenLogin, idPetani, idLahan, idPeriode);

                            setRvDokumentasi(getApplicationContext(), data,
                                    tokenLogin, idPetani, idLahan, idPeriode);

//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                CalendarUtils.selectedDate = LocalDate.now();
//                                filter(CalendarUtils.selectedDate.toString());
//                            }

                            filter(CalendarUtils.selectedDate.toString());

                        } else {

                            preferences = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    JadwalActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        String message = response.body().getMessage();
                        Toast.makeText(JadwalActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JadwalActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JadwalActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOne> call, Throwable t) {

                swipeRefresh.setRefreshing(false);

                Toast.makeText(JadwalActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void initWidgets(String tokenLogin, String idPetani,
                             String idLahan, String idPeriode) {

        swipeRefresh = findViewById(R.id.swipe_refresh);

        rvActivity = findViewById(R.id.rv_activity);
        rvPengamatan = findViewById(R.id.rv_pengamatan);
        rvDokumentasi = findViewById(R.id.rv_dokumentasi);
        rvCatatan = findViewById(R.id.rv_catatan);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);

        txtNamaPetani = findViewById(R.id.txt_nama_petani);

        activityAdapter = new ActivityAdapter(getApplicationContext(),
                listActivity, tokenLogin, idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvActivity.setLayoutManager(layoutManager);
        rvActivity.setAdapter(activityAdapter);

        pengamatanAdapter = new PengamatanAdapter(getApplicationContext(),
                listPengamatan, tokenLogin, idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager pengamatanLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        rvPengamatan.setLayoutManager(pengamatanLayoutManager);
        rvPengamatan.setAdapter(pengamatanAdapter);

        dokumentasiAdapter = new DokumentasiAdapter(getApplicationContext(),
                listDokumentasi, tokenLogin, idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager dokumentasiLayoutManager = new GridLayoutManager(
                getApplicationContext(), 2);
        rvDokumentasi.setLayoutManager(dokumentasiLayoutManager);
        rvDokumentasi.setAdapter(dokumentasiAdapter);

        catatanAdapter = new CatatanAdapter(getApplicationContext(), listRating, tokenLogin,
                idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager catatanLayoutManager = new LinearLayoutManager(
                getApplicationContext());
        rvCatatan.setLayoutManager(catatanLayoutManager);
        rvCatatan.setAdapter(catatanAdapter);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        FormAddActivity.class);
                intent.putExtra("selectedDate", CalendarUtils.selectedDate.toString());
                startActivity(intent);
            }
        });

        btnActivity = findViewById(R.id.btn_activity);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView("activity");
            }
        });

        btnPengamatan = findViewById(R.id.btn_pengamatan);
        btnPengamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView("pengamatan");
            }
        });

        btnDokumentasi = findViewById(R.id.btn_dokumentasi);
        btnDokumentasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView("dokumentasi");
            }
        });

        btnCatatan = findViewById(R.id.btn_catatan);
        btnCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView("catatan");
            }
        });

        btnActivity.setBackgroundResource(R.drawable.border_blue);

        btnPengamatan.setBackgroundResource(R.drawable.custom_border_grey);
        btnDokumentasi.setBackgroundResource(R.drawable.custom_border_grey);
        btnCatatan.setBackgroundResource(R.drawable.custom_border_grey);

        Calendar calendar = Calendar.getInstance();;

        SimpleDateFormat sdfHari = new SimpleDateFormat("EEE");
        String hari = sdfHari.format(calendar.getTime());

        SimpleDateFormat sdfTanggal = new SimpleDateFormat("dd");
        String tanggal = sdfTanggal.format(calendar.getTime());

        SimpleDateFormat sdfBulan = new SimpleDateFormat("MMM yyyy");
        String bulan = sdfBulan.format(calendar.getTime());

        txtTanggal = findViewById(R.id.txt_tanggal);
        txtTanggal.setText(tanggal);

        txtHari = findViewById(R.id.txt_hari);
        txtHari.setText(hari);

        txtBulan = findViewById(R.id.txt_bulan);
        txtBulan.setText(bulan);

        lytTodayDate = findViewById(R.id.lyt_today_date);
        lytTodayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });

        lytRvDokumentasi = findViewById(R.id.lyt_rv_dokumentasi);

        monthYearText = findViewById(R.id.monthYearTV);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetOneBody getOneBody = new GetOneBody();
                getOneBody.setUserIdInt(idPetani);
                getOneBody.setLandCodeVar(idLahan);
                getOneBody.setPeriodPlantTxt(idPeriode);

                GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                Call<GetOne> getOneCall = service.getOneCultivation(tokenLogin, getOneBody);
                getOneCall.enqueue(new Callback<GetOne>() {
                    @Override
                    public void onResponse(Call<GetOne> call, Response<GetOne> response) {

                        swipeRefresh.setRefreshing(false);

                        if(response.code() == 200) {
                            if (response.body() != null) {

                                SharedPreferences preferences;

                                if(response.body().getCode() == 0) {

                                    preferences = getSharedPreferences(
                                            "MySharedPref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("idPetani", idPetani);
                                    editor.putString("idLahan", idLahan);
                                    editor.putString("idPeriode", idPeriode);
                                    editor.apply();

                                    data = response.body().getData();

                                    setRvActivity(getApplicationContext(), data,
                                            tokenLogin, idPetani,
                                            idLahan, idPeriode);

                                    setRvPengamatan(getApplicationContext(), data,
                                            tokenLogin, idPetani, idLahan, idPeriode);

                                    setRvCatatan(getApplicationContext(), data,
                                            tokenLogin, idPetani, idLahan, idPeriode);

                                    setRvDokumentasi(getApplicationContext(), data,
                                            tokenLogin, idPetani, idLahan, idPeriode);

//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                        CalendarUtils.selectedDate = LocalDate.now();
//                                        filter(CalendarUtils.selectedDate.toString());
//                                    }

                                    filter(CalendarUtils.selectedDate.toString());

                                } else {

                                    preferences = getSharedPreferences(
                                            "MySharedPref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isUserLogin", false);
                                    editor.apply();

                                    Intent intent = new Intent(
                                            JadwalActivity.this,
                                            LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                String message = response.body().getMessage();
                                Toast.makeText(JadwalActivity.this, message,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(JadwalActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(JadwalActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOne> call, Throwable t) {

                        swipeRefresh.setRefreshing(false);

                        Toast.makeText(JadwalActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                        Log.e("Failure", t.toString());
                    }
                });

            }
        });

        btnCari = findViewById(R.id.btn_cari_jadwal);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllField()) {

                    ProgressDialog progressDialog = new ProgressDialog(JadwalActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    GetOneBody getOneBody = new GetOneBody();
                    getOneBody.setUserIdInt(String.valueOf(idPetaniSpinner));
                    getOneBody.setLandCodeVar(idLahanSpinner);
                    getOneBody.setPeriodPlantTxt(idPeriodeSpinner);

                    SharedPreferences sh = JadwalActivity.this.getSharedPreferences("MySharedPref",
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

                                        preferences = getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("idPetani", idPetaniSpinner);
                                        editor.putString("namaPetani", namaPetani);

                                        Set<String> petaniListSet = new HashSet<>(petaniList);
                                        editor.putStringSet("petaniList", petaniListSet);

                                        Set<String> idPetaniListSet = new HashSet<>(idPetaniList);
                                        editor.putStringSet("idPetaniList", idPetaniListSet);

                                        editor.putString("idLahan", idLahanSpinner);
                                        editor.putString("namaLahan", namaLahan);

                                        Set<String> lahanListSet = new HashSet<>(lahanList);
                                        editor.putStringSet("lahanList", lahanListSet);

                                        Set<String> idLahanListSet = new HashSet<>(idLahanList);
                                        editor.putStringSet("idLahanList", idLahanListSet);

                                        editor.putString("idPeriode", idPeriodeSpinner);
                                        editor.putString("namaPeriode", idPeriodeSpinner);

                                        Set<String> periodeListSet = new HashSet<>(periodeList);
                                        editor.putStringSet("periodeList", periodeListSet);

                                        editor.apply();

                                        data = response.body().getData();

                                        setRvActivity(getApplicationContext(), data,
                                                tokenLogin, idPetani,
                                                idLahan, idPeriode);

                                        setRvPengamatan(getApplicationContext(), data,
                                                tokenLogin, idPetani, idLahan, idPeriode);

                                        setRvCatatan(getApplicationContext(), data,
                                                tokenLogin, idPetani, idLahan, idPeriode);

                                        setRvDokumentasi(getApplicationContext(), data,
                                                tokenLogin, idPetani, idLahan, idPeriode);

//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                        CalendarUtils.selectedDate = LocalDate.now();
//                                        filter(CalendarUtils.selectedDate.toString());
//                                    }

                                        filter(CalendarUtils.selectedDate.toString());
                                    } else {

                                        preferences = JadwalActivity.this.getSharedPreferences(
                                                "MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isUserLogin", false);
                                        editor.apply();

                                        Intent intent = new Intent(JadwalActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        JadwalActivity.this.finish();
                                    }

                                    Toast.makeText(JadwalActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(JadwalActivity.this,
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(JadwalActivity.this,
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetOne> call, Throwable t) {
                            progressDialog.dismiss();

                            Toast.makeText(JadwalActivity.this,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Failure", t.toString());
                        }
                    });
                }
            }
        });

//        swipeRefresh.setRefreshing(true);
    }

    private void openCalendar() {
        // on below line we are getting
        // the instance of our calendar.
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int yearNow = c.get(Calendar.YEAR);
        int monthNow = c.get(Calendar.MONTH);
        int dayNow = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                JadwalActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar mCalendar = Calendar.getInstance();
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).
//                                format(mCalendar.getTime());

                        String date = year + "-" + String.format("%02d", (monthOfYear + 1))
                                + "-" + String.format("%02d", (dayOfMonth));
                        LocalDate localDate = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            localDate = LocalDate.parse(date);
                        }

                        CalendarUtils.selectedDate = localDate;
                        setWeekView();

                        filter(date);

//                        Toast.makeText(getApplicationContext(), String.valueOf(localDate),
//                                Toast.LENGTH_SHORT).show();
//
////                        txtTglPenjemuran.setText(selectedDate);
//
////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
////                        String tglPenjemuran = simpleDateFormat.format(mCalendar.getTime());
//
                        SimpleDateFormat sdfHari = new SimpleDateFormat("EEE");
                        String hari = sdfHari.format(mCalendar.getTime());
                        txtHari.setText(hari);

                        SimpleDateFormat sdfTanggal = new SimpleDateFormat("dd");
                        String tanggal = sdfTanggal.format(mCalendar.getTime());
                        txtTanggal.setText(tanggal);

                        SimpleDateFormat sdfBulan = new SimpleDateFormat("MMM yyyy");
                        String bulan = sdfBulan.format(mCalendar.getTime());
                        txtBulan.setText(bulan);


                        // on below line we are setting date to our text view.
//                        selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                yearNow, monthNow, dayNow);

        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }

    private void setView(String opsi) {

        switch (opsi) {
            case "activity":

                rvActivity.setVisibility(View.VISIBLE);

                rvPengamatan.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);
                rvDokumentasi.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                btnActivity.setBackgroundResource(R.drawable.border_blue);

                btnPengamatan.setBackgroundResource(R.drawable.custom_border_grey);
                btnDokumentasi.setBackgroundResource(R.drawable.custom_border_grey);
                btnCatatan.setBackgroundResource(R.drawable.custom_border_grey);

                break;
            case "pengamatan":

                rvPengamatan.setVisibility(View.VISIBLE);

                rvActivity.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);
                rvDokumentasi.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                btnPengamatan.setBackgroundResource(R.drawable.border_blue);

                btnActivity.setBackgroundResource(R.drawable.custom_border_grey);
                btnDokumentasi.setBackgroundResource(R.drawable.custom_border_grey);
                btnCatatan.setBackgroundResource(R.drawable.custom_border_grey);

                break;
            case "dokumentasi":

                lytRvDokumentasi.setVisibility(View.VISIBLE);
                rvDokumentasi.setVisibility(View.VISIBLE);

                rvPengamatan.setVisibility(View.GONE);
                rvActivity.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                btnDokumentasi.setBackgroundResource(R.drawable.border_blue);

                btnActivity.setBackgroundResource(R.drawable.custom_border_grey);
                btnPengamatan.setBackgroundResource(R.drawable.custom_border_grey);
                btnCatatan.setBackgroundResource(R.drawable.custom_border_grey);

                break;
            case "catatan":

                rvCatatan.setVisibility(View.VISIBLE);

                rvDokumentasi.setVisibility(View.GONE);
                rvPengamatan.setVisibility(View.GONE);
                rvActivity.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);

                btnCatatan.setBackgroundResource(R.drawable.border_blue);

                btnActivity.setBackgroundResource(R.drawable.custom_border_grey);
                btnDokumentasi.setBackgroundResource(R.drawable.custom_border_grey);
                btnPengamatan.setBackgroundResource(R.drawable.custom_border_grey);

                break;
        }
    }

    private void setRvActivity(Context context, Data data, String tokenLogin,
                               String idPetani, String idLahan, String idPeriode) {

        listActivity = data.getActivity();
        activityAdapter.filterList(listActivity);
//        activityAdapter = new ActivityAdapter(context,
//                data.JadwalActivity.this, tokenLogin, idPetani, idLahan, idPeriode);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        rvActivity.setLayoutManager(layoutManager);
//        rvActivity.setAdapter(activityAdapter);
    }

    private void setRvPengamatan(Context context, Data data, String tokenLogin,
                                 String idPetani, String idLahan, String idPeriode) {

        listPengamatan = data.getObservation();
        pengamatanAdapter.filterList(listPengamatan);
//        pengamatanAdapter = new PengamatanAdapter(context,
//                data.getObservation(), tokenLogin, idPetani, idLahan, idPeriode);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        rvPengamatan.setLayoutManager(layoutManager);
//        rvPengamatan.setAdapter(pengamatanAdapter);
    }

    private void setRvDokumentasi(Context context, Data data, String tokenLogin,
                                  String idPetani, String idLahan, String idPeriode) {

        listDokumentasi = data.getDocumentation();
        dokumentasiAdapter.filterList(listDokumentasi);

//        dokumentasiAdapter = new DokumentasiAdapter(context,
//                data.getDocumentation(), tokenLogin, idPetani, idLahan, idPeriode);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
//        rvDokumentasi.setLayoutManager(layoutManager);
//        rvDokumentasi.setAdapter(dokumentasiAdapter);
    }

    private void setRvCatatan(Context context, Data data, String tokenLogin,
                              String idPetani, String idLahan, String idPeriode) {

        listRating = data.getRating();
        catatanAdapter.filterList(listRating);

//        catatanAdapter = new CatatanAdapter(context, data.getRating(), tokenLogin,
//                idPetani, idLahan, idPeriode);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        rvCatatan.setLayoutManager(layoutManager);
//        rvCatatan.setAdapter(catatanAdapter);
    }

    private void initSpinner(String tokenLogin, String idPetani,
                             String idLahan, String idPeriode) {

        spPetani = findViewById(R.id.sp_petani);
        spLahan = findViewById(R.id.sp_lahan);
        spPeriode = findViewById(R.id.sp_periode);

        setSpinnerPetani(tokenLogin, idPetani, idLahan, idPeriode);
//        txtNamaPetani.setText(namaPetani);
//        spPetani.setHint("");

        spPetani.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

//                txtNamaPetani.setVisibility(View.GONE);
//                spPetani.setHint("Pilih Petani");

                idPetaniSpinner = idPetaniList.get(position);
                namaPetani = petaniList.get(position);

                idLahanList = new ArrayList<>();
                idLahanSpinner = "";
                spLahan.setItem(idLahanList);

                periodeList = new ArrayList<>();
                idPeriodeSpinner = "";
                spPeriode.setItem(periodeList);

                setSpinnerLahan(tokenLogin, idPetaniSpinner);

//                Toast.makeText(JadwalActivity.this, idPetani,
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

                idLahanSpinner = idLahanList.get(i);
                namaLahan = lahanList.get(i);

                setSpinnerPeriode(tokenLogin, idLahanSpinner);

//                Toast.makeText(JadwalActivity.this, idLahan,
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

                idPeriodeSpinner = periodeList.get(i);

//                Toast.makeText(JadwalActivity.this, idPeriode,
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
                        Toast.makeText(JadwalActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JadwalActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFilterPeriode> call, Throwable t) {
                Toast.makeText(JadwalActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerLahan(String tokenLogin, String idPetani) {

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DropdownFilterLahan> dropdownFilterLahanCall = service.dropdownFilterLahan(tokenLogin,
                Integer.parseInt(idPetani));
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
                            spLahan.setHint("Pilih Lahan");
                            spPeriode.setHint("Pilih Periode");
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_LAHAN", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JadwalActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JadwalActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFilterLahan> call, Throwable t) {
                Toast.makeText(JadwalActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void setSpinnerPetani(String tokenLogin, String idPetani,
                                  String idLahan, String idPeriode) {

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
                                idPetaniList.add(String.valueOf(listData.get(i).getIdSeq()));
                            }

                            spPetani.setItem(petaniList);

                            idPetaniSpinner =  idPetani;
                            int indexPetani = petaniList.indexOf(namaPetani);
                            spPetani.setHint(namaPetani);

                            spLahan.setItem(lahanList);

                            idLahanSpinner =  idLahan;
                            int indexLahan = lahanList.indexOf(namaLahan);
                            spLahan.setHint(namaLahan);

                            spPeriode.setItem(periodeList);

                            idPeriodeSpinner =  idPeriode;
                            int indexPeriode = periodeList.indexOf(idPeriode);
                            spPeriode.setHint(idPeriode);
                        }

                        String message = response.body().getMessage();
                        Log.e("SP_PETANI", message);

//                        Toast.makeText(EditPengamatanActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JadwalActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JadwalActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DropdownFarmer> call, Throwable t) {
                Toast.makeText(JadwalActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private boolean checkAllField() {

        if(idPetaniSpinner == null || idPetaniSpinner.equals("")) {
            Toast.makeText(JadwalActivity.this, "Pilih petani terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idLahanSpinner == null || idLahanSpinner.equals("")) {
            Toast.makeText(JadwalActivity.this, "Pilih lahan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(idPeriodeSpinner == null || idPeriodeSpinner.equals("")) {
            Toast.makeText(JadwalActivity.this, "Pilih periode terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // after all validation return true.
        return true;
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

//        Toast.makeText(JadwalActivity.this,
//                String.valueOf(days.size()),
//                Toast.LENGTH_SHORT).show();

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),
                7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        }
        setWeekView();
    }

    public void nextWeekAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        }
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public void onItemClick(int position, LocalDate date) {

        CalendarUtils.selectedDate = date;
        setWeekView();

        filter(date.toString());

//        Toast.makeText(JadwalActivity.this,
//                date.toString(),
//                Toast.LENGTH_SHORT).show();
    }

    private void filter(String text) {

        // creating a new array list to filter our data.
        List<Activity> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Activity item : listActivity) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTimeCalenderDte().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }

        activityAdapter.filterList(filteredlist);

        // creating a new array list to filter our data.
        List<Observation> filteredPengamatan = new ArrayList<>();

        // running a for loop to compare elements.
        for (Observation item : listPengamatan) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTimeCalenderDte().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredPengamatan.add(item);
            }
        }

        pengamatanAdapter.filterList(filteredPengamatan);

        // creating a new array list to filter our data.
        List<Documentation> filteredDokumentasi = new ArrayList<>();

        // running a for loop to compare elements.
        for (Documentation item : listDokumentasi) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTimeCalenderDte().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredDokumentasi.add(item);
            }
        }

        dokumentasiAdapter.filterList(filteredDokumentasi);

        // creating a new array list to filter our data.
        List<Rating> filteredRating = new ArrayList<>();

//        Toast.makeText(JadwalActivity.this, String.valueOf(filteredlist.size()),
//                Toast.LENGTH_SHORT).show();

        // running a for loop to compare elements.
        for (Rating item : listRating) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTimeCalenderDte().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredRating.add(item);
            }
        }

//        Toast.makeText(JadwalActivity.this, String.valueOf(filteredRating.size()),
//                Toast.LENGTH_SHORT).show();

        catatanAdapter.filterList(filteredRating);

//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(JadwalActivity.this, "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            dataPanenAdapter.filterList(filteredlist);
//        }
    }
}