package com.example.agroobot_fms;

import static com.example.agroobot_fms.utils.CalendarUtils.daysInWeekArray;
import static com.example.agroobot_fms.utils.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.adapter.ActivityAdapter;
import com.example.agroobot_fms.adapter.CalendarAdapter;
import com.example.agroobot_fms.adapter.CatatanAdapter;
import com.example.agroobot_fms.adapter.DokumentasiAdapter;
import com.example.agroobot_fms.adapter.EventAdapter;
import com.example.agroobot_fms.adapter.PengamatanAdapter;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.Event;
import com.example.agroobot_fms.model.get_one.Activity;
import com.example.agroobot_fms.model.get_one.Data;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.utils.CalendarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private LocalDate selectedDate;
    private JsonObject dataJadwal;

    private RecyclerView calendarRecyclerView, rvActivity, rvPengamatan;
    private RecyclerView rvDokumentasi, rvCatatan;
    private RelativeLayout btnAdd;
    CardView lytRvDokumentasi;
    private TextView monthYearText;
    TextView btnActivity, btnPengamatan, btnDokumentasi, btnCatatan;
    TextView txtTanggal, txtHari, txtBulan;
    SwipeRefreshLayout swipeRefresh;

    ActivityAdapter activityAdapter;
    private List<Activity> listActivity;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        SharedPreferences sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");
        String idPetani = sh.getString("idPetani", "");
        String idLahan = sh.getString("idLahan", "");
        String idPeriode = sh.getString("idPeriode", "");

        if (getIntent().getExtras() != null) {

            String dataJson = getIntent().getStringExtra("dataJadwal");

            if(dataJson != null) {

                Gson gson = new Gson();
                data = gson.fromJson(dataJson, Data.class);

                initWidgets(tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvActivity(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvPengamatan(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvCatatan(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

                setRvDokumentasi(getApplicationContext(), data, tokenLogin, idPetani,
                        idLahan, idPeriode);

//                Toast.makeText(this, data.getActivity().get(0).getActivityTxt(),
//                        Toast.LENGTH_SHORT).show();
            }
        }

        CalendarUtils.selectedDate = LocalDate.now();

        setWeekView();
    }

    private void initWidgets(String tokenLogin, String idPetani,
                             String idLahan, String idPeriode) {

        swipeRefresh = findViewById(R.id.swipe_refresh);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        FormAddActivity.class);
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

        rvActivity = findViewById(R.id.rv_activity);
        rvPengamatan = findViewById(R.id.rv_pengamatan);
        rvDokumentasi = findViewById(R.id.rv_dokumentasi);
        rvCatatan = findViewById(R.id.rv_catatan);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);

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

                                    setRvPengamatan(getApplicationContext(), data, tokenLogin, idPetani, idLahan, idPeriode);

                                    setRvCatatan(getApplicationContext(), data, tokenLogin, idPetani, idLahan, idPeriode);

                                    setRvDokumentasi(getApplicationContext(), data, tokenLogin, idPetani, idLahan, idPeriode);

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
    }

    private void setView(String opsi) {

        switch (opsi) {
            case "activity":

                rvActivity.setVisibility(View.VISIBLE);

                rvPengamatan.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);
                rvDokumentasi.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                break;
            case "pengamatan":

                rvPengamatan.setVisibility(View.VISIBLE);

                rvActivity.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);
                rvDokumentasi.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                break;
            case "dokumentasi":

                lytRvDokumentasi.setVisibility(View.VISIBLE);
                rvDokumentasi.setVisibility(View.VISIBLE);

                rvPengamatan.setVisibility(View.GONE);
                rvActivity.setVisibility(View.GONE);
                rvCatatan.setVisibility(View.GONE);

                break;
            case "catatan":

                rvCatatan.setVisibility(View.VISIBLE);

                rvDokumentasi.setVisibility(View.GONE);
                rvPengamatan.setVisibility(View.GONE);
                rvActivity.setVisibility(View.GONE);
                lytRvDokumentasi.setVisibility(View.GONE);

                break;
        }
    }

    private void setRvActivity(Context context, Data data, String tokenLogin,
                               String idPetani, String idLahan, String idPeriode) {

        listActivity = data.getActivity();
        activityAdapter = new ActivityAdapter(context,
                data.getActivity(), tokenLogin, idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvActivity.setLayoutManager(layoutManager);
        rvActivity.setAdapter(activityAdapter);
    }

    private void setRvPengamatan(Context context, Data data, String tokenLogin,
                                 String idPetani, String idLahan, String idPeriode) {
        PengamatanAdapter pengamatanAdapter = new PengamatanAdapter(context,
                data.getObservation(), tokenLogin, idPetani, idLahan, idPeriode) ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvPengamatan.setLayoutManager(layoutManager);
        rvPengamatan.setAdapter(pengamatanAdapter);
    }

    private void setRvDokumentasi(Context context, Data data, String tokenLogin,
                                  String idPetani, String idLahan, String idPeriode) {
        DokumentasiAdapter dokumentasiAdapter = new DokumentasiAdapter(context,
                data.getDocumentation(), tokenLogin, idPetani, idLahan, idPeriode);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvDokumentasi.setLayoutManager(layoutManager);
        rvDokumentasi.setAdapter(dokumentasiAdapter);
    }

    private void setRvCatatan(Context context, Data data, String tokenLogin,
                              String idPetani, String idLahan, String idPeriode) {
        CatatanAdapter catatanAdapter = new CatatanAdapter(context, data.getRating());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvCatatan.setLayoutManager(layoutManager);
        rvCatatan.setAdapter(catatanAdapter);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
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

//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            dataPanenAdapter.filterList(filteredlist);
//        }
    }
}