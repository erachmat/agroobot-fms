package com.example.agroobot_fms;

import static com.example.agroobot_fms.utils.CalendarUtils.daysInWeekArray;
import static com.example.agroobot_fms.utils.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        if (getIntent().getExtras() != null) {

            String dataJson = getIntent().getStringExtra("dataJadwal");
            if(dataJson != null){

                Gson gson = new Gson();
                Data data = gson.fromJson(dataJson, Data.class);

                initWidgets();

                setRvActivity(getApplicationContext(), data);

                setRvPengamatan(getApplicationContext(), data);

                setRvCatatan(getApplicationContext(), data);

                setRvDokumentasi(getApplicationContext(), data);

//                Toast.makeText(this, data.getActivity().get(0).getActivityTxt(),
//                        Toast.LENGTH_SHORT).show();
            }
        }

        CalendarUtils.selectedDate = LocalDate.now();

        setWeekView();
    }

    private void initWidgets() {

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

    private void setRvActivity(Context context, Data data) {
        ActivityAdapter activityAdapter = new ActivityAdapter(context,
                data.getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvActivity.setLayoutManager(layoutManager);
        rvActivity.setAdapter(activityAdapter);
    }

    private void setRvPengamatan(Context context, Data data) {
        PengamatanAdapter pengamatanAdapter = new PengamatanAdapter(context,
                data.getObservation());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvPengamatan.setLayoutManager(layoutManager);
        rvPengamatan.setAdapter(pengamatanAdapter);
    }

    private void setRvDokumentasi(Context context, Data data) {
        DokumentasiAdapter dokumentasiAdapter = new DokumentasiAdapter(context,
                data.getDocumentation());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvDokumentasi.setLayoutManager(layoutManager);
        rvDokumentasi.setAdapter(dokumentasiAdapter);
    }

    private void setRvCatatan(Context context, Data data) {
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
    }
}