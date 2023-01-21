package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.get_one_budget_detail.Datum;
import com.example.agroobot_fms.model.get_one_budget_detail.GetOneBudgetDetail;
import com.example.agroobot_fms.model.get_one_data_panen.Data;
import com.example.agroobot_fms.model.get_one_data_panen.DataPanen;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnggaranActivity extends AppCompatActivity {

    TextView txtKategori, txtKegiatan, txtLuas, txtSatuan, txtJumlah;
    TextView txtTotalHarga, txtHarga;
    ImageView btnEdit, btnDelete, imgDokumentasi;
    private ProgressDialog progressDialog;

    private SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anggaran);

        if (getIntent().getExtras() != null) {

            String idSeq = getIntent().getStringExtra("idSeq");

            initView(idSeq);
            initData(idSeq);
        }
    }

    private void initView(String idSeq) {

        txtKategori = findViewById(R.id.txt_kategori);
        txtKegiatan = findViewById(R.id.txt_kegiatan);
        txtLuas = findViewById(R.id.txt_luas);
        txtSatuan = findViewById(R.id.txt_satuan);
        txtJumlah = findViewById(R.id.txt_jumlah);
        txtHarga = findViewById(R.id.txt_harga);
        txtTotalHarga = findViewById(R.id.txt_total_harga);

        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
        imgDokumentasi = findViewById(R.id.img_dokumentasi);

    }

    private void initData(String idSeq) {

        progressDialog = new ProgressDialog(DetailAnggaranActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sh = getSharedPreferences("MySharedPref",
                Context.MODE_PRIVATE);
        String tokenLogin = sh.getString("tokenLogin", "");

        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
        Call<GetOneBudgetDetail> getOneBudgetDetailCall = service.getOneBudgetDetail(
                tokenLogin, Integer.parseInt(idSeq));
        getOneBudgetDetailCall.enqueue(new Callback<GetOneBudgetDetail>() {
            @Override
            public void onResponse(Call<GetOneBudgetDetail> call,
                                   Response<GetOneBudgetDetail> response) {

                progressDialog.dismiss();

                if(response.code() == 200) {
                    if (response.body() != null) {
                        if(response.body().getCode() == 0) {

                            Datum dataItem = response.body().getData().get(0);

                            txtKategori.setText(dataItem.getCategoryVar());
                            txtKegiatan.setText(dataItem.getActivityTxt());
                            txtLuas.setText(dataItem.getAreaVar());
                            txtJumlah.setText(dataItem.getQuantityVar());
                            txtSatuan.setText(dataItem.getSatuanVar());

                            String hargaRp = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(Integer.parseInt(dataItem.getPriceVar()));
                            hargaRp = "IDR " + hargaRp;
                            txtHarga.setText(hargaRp);

                            String totalHarga = NumberFormat.getInstance(Locale.ENGLISH).
                                    format(Integer.parseInt(dataItem.getTotalPriceVar()));
                            totalHarga = "IDR " + totalHarga;
                            txtTotalHarga.setText(totalHarga);

                            if(!dataItem.getDocumentTxt().equals("")) {
                                Picasso.Builder builder = new Picasso.Builder(
                                        DetailAnggaranActivity.this);
                                builder.downloader(new OkHttp3Downloader(
                                        DetailAnggaranActivity.this));
                                builder.build().load(dataItem.getDocumentTxt())
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
                                    DetailAnggaranActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        String message = response.body().getMessage();
                        Toast.makeText(DetailAnggaranActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailAnggaranActivity.this,
                                "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailAnggaranActivity.this,
                            "Something went wrong...Please try later!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneBudgetDetail> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailAnggaranActivity.this,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.toString());
            }
        });

    }
}