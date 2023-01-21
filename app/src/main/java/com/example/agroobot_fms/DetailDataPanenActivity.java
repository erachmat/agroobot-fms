package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.delete_data_panen.DeleteDataPanen;
import com.example.agroobot_fms.model.get_one_data_panen.Data;
import com.example.agroobot_fms.model.get_one_data_panen.DataPanen;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDataPanenActivity extends AppCompatActivity {

    TextView txtKomoditas, txtLahan, txtPeriodeTanam, txtHasilPanen;
    TextView txtTglPanen, txtTglPenjemuran, txtHasilPenjemuran;
    TextView txtTglPenggilingan, txtHasilPenggilingan;
    RoundedImageView imgDokumentasi;
    ImageView btnEdit, btnDelete;
    private ProgressDialog progressDialog;

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_panen);

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");

            initView(idSeq);
            initData(idSeq);
        }
    }

    private void initView(String idSeq) {

        txtKomoditas = findViewById(R.id.txt_komoditas);
        txtLahan = findViewById(R.id.txt_lahan);
        txtPeriodeTanam = findViewById(R.id.txt_periode_tanam);
        txtHasilPanen = findViewById(R.id.txt_hasil_panen);
        txtTglPanen = findViewById(R.id.txt_tgl_panen);
        txtTglPenjemuran = findViewById(R.id.txt_tgl_penjemuran);
        txtHasilPenjemuran = findViewById(R.id.txt_hasil_penjemuran);
        txtTglPenggilingan = findViewById(R.id.txt_tgl_penggilingan);
        txtHasilPenggilingan = findViewById(R.id.txt_hasil_penggilingan);

        imgDokumentasi = findViewById(R.id.img_dokumentasi);

        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(DetailDataPanenActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Hapus Data Panen")
                        .setMessage("Anda yakin menghapus data ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteDataPanen(idSeq);

//                                finish();
//                                Toast.makeText(DetailDataPanenActivity.this,
//                                        "Activity closed",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Tidak", null).show();

            }
        });

    }

    private void deleteDataPanen(String idSeq) {

        progressDialog = new ProgressDialog(DetailDataPanenActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DeleteDataPanen> deleteDataPanenCall = service.deleteDataPanen(
                Integer.parseInt(idSeq), tokenLogin);
        deleteDataPanenCall.enqueue(new Callback<DeleteDataPanen>() {
            @Override
            public void onResponse(Call<DeleteDataPanen> call, Response<DeleteDataPanen> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            finish();
                            Toast.makeText(DetailDataPanenActivity.this,
                                    "Silahkan refresh kembali table data panen!",
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            sh = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    DetailDataPanenActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        String message = response.body().getMessage();
//                        Toast.makeText(DetailDataPanenActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailDataPanenActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailDataPanenActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteDataPanen> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDataPanenActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }

    private void initData(String idSeq) {

        progressDialog = new ProgressDialog(DetailDataPanenActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<DataPanen> dataPanenCall = service.getOneDataPanen(tokenLogin, Integer.parseInt(idSeq));
        dataPanenCall.enqueue(new Callback<DataPanen>() {
            @Override
            public void onResponse(Call<DataPanen> call, Response<DataPanen> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            Data dataItem = response.body().getData();

                            txtKomoditas.setText(dataItem.getCommodityNameVar());

                            String kodeLahan = "[" + dataItem.getLandCodeVar() + "]";
                            txtLahan.setText(kodeLahan);

                            String periodeTanam = "Periode " + dataItem.getPeriodPlantTxt();
                            txtPeriodeTanam.setText(periodeTanam);

                            txtHasilPanen.setText(String.valueOf(dataItem.getHarvestFlo()));

                            String tglPanen = dataItem.getHarvestOnDte().substring(0, 10);
                            tglPanen = tglPanen.replace("-", "/");
                            txtTglPanen.setText(tglPanen);

                            txtHasilPenjemuran.setText(String.valueOf(
                                    dataItem.getHarvestDryingFlo()));

                            String tglPenjemuran = dataItem.getHarvestDryingDte().substring(0, 10);
                            tglPenjemuran = tglPenjemuran.replace("-", "/");
                            txtTglPenjemuran.setText(tglPenjemuran);

                            txtHasilPenggilingan.setText(String.valueOf(
                                    dataItem.getHarvestMillingFlo()));

                            String tglPenggilingan = dataItem.getHarvestMillingDte().
                                    substring(0, 10);
                            tglPenggilingan = tglPenggilingan.replace("-", "/");
                            txtTglPenggilingan.setText(tglPenggilingan);

                            if(dataItem.getDocumentTxt().size() != 0) {
                                Picasso.Builder builder = new Picasso.Builder(
                                        DetailDataPanenActivity.this);
                                builder.downloader(new OkHttp3Downloader(
                                        DetailDataPanenActivity.this));
                                builder.build().load(dataItem.getDocumentTxt().get(0))
                                        .error(R.drawable.img_dokumentasi)
                                        .into(imgDokumentasi);
                            }

                        } else {

                            sh = getSharedPreferences(
                                    "MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("isUserLogin", false);
                            editor.apply();

                            Intent intent = new Intent(
                                    DetailDataPanenActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        String message = response.body().getMessage();
                        Toast.makeText(DetailDataPanenActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailDataPanenActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailDataPanenActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPanen> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDataPanenActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });
    }
}