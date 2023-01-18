package com.example.agroobot_fms;

import static com.example.agroobot_fms.utils.CalendarUtils.daysInWeekArray;
import static com.example.agroobot_fms.utils.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.adapter.ActivityAdapter;
import com.example.agroobot_fms.adapter.CalendarAdapter;
import com.example.agroobot_fms.adapter.CatatanAdapter;
import com.example.agroobot_fms.adapter.DokumentasiAdapter;
import com.example.agroobot_fms.adapter.EventAdapter;
import com.example.agroobot_fms.adapter.PengamatanAdapter;
import com.example.agroobot_fms.model.Event;
import com.example.agroobot_fms.model.get_one.Data;
import com.example.agroobot_fms.utils.CalendarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JadwalActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView, rvActivity, rvPengamatan;
    private RecyclerView rvDokumentasi, rvCatatan;
    private LocalDate selectedDate;
    private JsonObject dataJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        if (getIntent().getExtras() != null) {

            String dogGson = getIntent().getStringExtra("dataJadwal");
            if(dogGson != null){
                Gson gson = new Gson();
                Data dog = gson.fromJson(dogGson, Data.class);
                Toast.makeText(this, dog.getActivity().get(0).getActivityTxt(),
                        Toast.LENGTH_SHORT).show();
            }
        }

        initWidgets();

        CalendarUtils.selectedDate = LocalDate.now();

        setWeekView();
        setRvActivity();
        setRvPengamatan();
        setRvDokumentasi();
        setRvCatatan();
    }

    private void initWidgets()
    {
        rvActivity = findViewById(R.id.rv_activity);
        rvPengamatan = findViewById(R.id.rv_pengamatan);
        rvDokumentasi = findViewById(R.id.rv_dokumentasi);
        rvCatatan = findViewById(R.id.rv_catatan);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setRvActivity() {

//        ActivityAdapter activityAdapter = new ActivityAdapter(getApplicationContext(),
//                dataJadwal.get("activity"));
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        rvActivity.setLayoutManager(layoutManager);
//        rvActivity.setAdapter(activityAdapter);
    }

    private void setRvPengamatan() {
        PengamatanAdapter pengamatanAdapter = new PengamatanAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPengamatan.setLayoutManager(layoutManager);
        rvPengamatan.setAdapter(pengamatanAdapter);
    }

    private void setRvDokumentasi() {
        DokumentasiAdapter dokumentasiAdapter = new DokumentasiAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvDokumentasi.setLayoutManager(layoutManager);
        rvDokumentasi.setAdapter(dokumentasiAdapter);
    }

    private void setRvCatatan() {
        CatatanAdapter catatanAdapter = new CatatanAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCatatan.setLayoutManager(layoutManager);
        rvCatatan.setAdapter(catatanAdapter);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
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
    }
}