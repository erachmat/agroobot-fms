package com.example.agroobot_fms.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.DetailAnggaranPetaniActivity;
import com.example.agroobot_fms.EditDetailAnggaranActivity;
import com.example.agroobot_fms.HomeActivity;
import com.example.agroobot_fms.LoginActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.ajukan_budget_detail.AjukanBudgetDetail;
import com.example.agroobot_fms.model.ajukan_budget_detail.AjukanBudgetDetailBody;
import com.example.agroobot_fms.model.ajukan_data_panen.AjukanPanenBody;
import com.example.agroobot_fms.model.batal_ajukan_budget_detail.BatalAjukanBudgetDetail;
import com.example.agroobot_fms.model.batal_ajukan_budget_detail.BatalAjukanBudgetDetailBody;
import com.example.agroobot_fms.model.delete_budget_detail.DeleteBudgetDetail;
import com.example.agroobot_fms.model.delete_data_panen.DeleteDataPanen;
import com.example.agroobot_fms.model.get_one_budget_plan.BudgetDetail;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnggaranAdapter extends RecyclerView.Adapter<DetailAnggaranAdapter.ViewHolder> {

    Context context;
    List<BudgetDetail> budgetDetail;
    String idAnggaran;

    public DetailAnggaranAdapter(Context context, List<BudgetDetail> budgetDetail, String idAnggaran) {
       this.context = context;
       this.budgetDetail = budgetDetail;
       this.idAnggaran = idAnggaran;
    }

    @NonNull
    @Override
    public DetailAnggaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_card_detail_anggaran,
                parent, false);
        return new DetailAnggaranAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAnggaranAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        BudgetDetail dataItem = budgetDetail.get(position);

        holder.txtKategori.setText(dataItem.getCategoryVar());
        holder.txtKegiatan.setText(dataItem.getActivityTxt());

        String totalRp = NumberFormat.getInstance(Locale.ENGLISH).
                    format(Integer.parseInt(dataItem.getTotalPriceVar()));
        totalRp = "Rp " + totalRp;
        holder.txtTotalNominal.setText(totalRp);

        switch (dataItem.getStatusNameVar()) {
            case "draft":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_grey);
                holder.txtStatus.setText(dataItem.getStatusNameVar());
                break;
            case "approval":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_blue);
                holder.txtStatus.setText("diproses");
                break;
            case "approved":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_green);
                holder.txtStatus.setText("disetujui");
                break;
            case "rejected":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_red);
                holder.txtStatus.setText("ditolak");
                break;
        }

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.lyt_dialog_detail_anggaran);

                TextView txtKategori = dialog.findViewById(R.id.txt_kategori);
                TextView txtKegiatan = dialog.findViewById(R.id.txt_kegiatan);
                TextView txtLuas = dialog.findViewById(R.id.txt_luas);
                TextView txtSatuan = dialog.findViewById(R.id.txt_satuan);
                TextView txtJumlah = dialog.findViewById(R.id.txt_jumlah);
                TextView txtHarga = dialog.findViewById(R.id.txt_harga);
                TextView txtTotalHarga = dialog.findViewById(R.id.txt_total_harga);

                ImageView btnClose = dialog.findViewById(R.id.btn_close);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                LinearLayout btnAjukan = dialog.findViewById(R.id.btn_ajukan);
                btnAjukan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog progressDialog = new ProgressDialog(
                                view.getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        SharedPreferences sh = view.getContext()
                                .getSharedPreferences("MySharedPref",
                                        Context.MODE_PRIVATE);
                        String tokenLogin = sh.getString("tokenLogin", "");
                        String fullnameVar = sh.getString("fullnameVar", "");

                        AjukanBudgetDetailBody ajukanBudgetDetailBody = new AjukanBudgetDetailBody();
                        ajukanBudgetDetailBody.setUpdatedByVar(fullnameVar);

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        Call<AjukanBudgetDetail> ajukanBudgetDetailCall = service.ajukanBudgetDetail(
                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin, ajukanBudgetDetailBody);
                        ajukanBudgetDetailCall.enqueue(new Callback<AjukanBudgetDetail>() {
                            @Override
                            public void onResponse(Call<AjukanBudgetDetail> call,
                                                   Response<AjukanBudgetDetail> response) {

                                progressDialog.dismiss();

                                if(response.code() == 200) {
                                    if (response.body() != null) {
                                        if(response.body().getCode() == 0) {

                                            dialog.dismiss();
                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    DetailAnggaranPetaniActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("idSeq", idAnggaran);
                                            view.getContext().startActivity(intent);

//                                            Toast.makeText(view.getContext(),
//                                                    "Silahkan refresh kembali table data panen!",
//                                                    Toast.LENGTH_SHORT).show();

                                        } else {

                                            SharedPreferences.Editor editor = sh.edit();
                                            editor.putBoolean("isUserLogin", false);
                                            editor.apply();

                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            view.getContext().startActivity(intent);
                                        }

                                        String message = response.body().getMessage();
                                        Toast.makeText(view.getContext(), message,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(),
                                                "Something went wrong...Please try later!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(view.getContext(),
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AjukanBudgetDetail> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(view.getContext(),
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                                Log.e("Failure", t.toString());
                            }
                        });
                    }
                });

                LinearLayout btnBatalAjukan = dialog.findViewById(R.id.btn_batal_ajukan);
                btnBatalAjukan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog progressDialog = new ProgressDialog(
                                view.getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        SharedPreferences sh = view.getContext()
                                .getSharedPreferences("MySharedPref",
                                        Context.MODE_PRIVATE);
                        String tokenLogin = sh.getString("tokenLogin", "");
                        String fullnameVar = sh.getString("fullnameVar", "");

                        BatalAjukanBudgetDetailBody batalAjukanBudgetDetailBody =
                                new BatalAjukanBudgetDetailBody();
                        batalAjukanBudgetDetailBody.setUpdatedByVar(fullnameVar);

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        Call<BatalAjukanBudgetDetail> batalAjukanBudgetDetailCall =
                                service.batalAjukanBudgetDetail(
                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin,
                                        batalAjukanBudgetDetailBody);
                        batalAjukanBudgetDetailCall.enqueue(new Callback<BatalAjukanBudgetDetail>() {
                            @Override
                            public void onResponse(Call<BatalAjukanBudgetDetail> call,
                                                   Response<BatalAjukanBudgetDetail> response) {
                                progressDialog.dismiss();

                                if(response.code() == 200) {
                                    if (response.body() != null) {
                                        if(response.body().getCode() == 0) {

                                            dialog.dismiss();
                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    DetailAnggaranPetaniActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("idSeq", idAnggaran);
                                            view.getContext().startActivity(intent);

//                                            Toast.makeText(view.getContext(),
//                                                    "Silahkan refresh kembali table data panen!",
//                                                    Toast.LENGTH_SHORT).show();

                                        } else {

                                            SharedPreferences.Editor editor = sh.edit();
                                            editor.putBoolean("isUserLogin", false);
                                            editor.apply();

                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            view.getContext().startActivity(intent);
                                        }

                                        String message = response.body().getMessage();
                                        Toast.makeText(view.getContext(), message,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(),
                                                "Something went wrong...Please try later!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(view.getContext(),
                                            "Something went wrong...Please try later!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BatalAjukanBudgetDetail> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(view.getContext(),
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                                Log.e("Failure", t.toString());
                            }
                        });
                    }
                });

                switch (dataItem.getStatusNameVar()) {
                    case "draft":
                        btnAjukan.setVisibility(View.VISIBLE);
                        btnBatalAjukan.setVisibility(View.GONE);
                        break;
                    case "approval":
                        btnBatalAjukan.setVisibility(View.VISIBLE);
                        btnAjukan.setVisibility(View.GONE);
                        break;
                    case "approved":
                    case "rejected":
                        btnAjukan.setVisibility(View.GONE);
                        btnBatalAjukan.setVisibility(View.GONE);
                        break;
                }

                ImageView btnEdit = dialog.findViewById(R.id.btn_edit);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),
                                EditDetailAnggaranActivity.class);
                        intent.putExtra("idSeq", dataItem.getIdSeq());
                        intent.putExtra("idAnggaran", idAnggaran);
                        view.getContext().startActivity(intent);
                    }
                });

                ImageView btnDelete = dialog.findViewById(R.id.btn_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AlertDialog.Builder(view.getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Hapus Data Anggaran")
                                .setMessage("Anda yakin menghapus data ini?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {

                                        ProgressDialog progressDialog = new ProgressDialog(
                                                view.getContext());
                                        progressDialog.setMessage("Loading....");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();

                                        SharedPreferences sh = view.getContext()
                                                .getSharedPreferences("MySharedPref",
                                                        Context.MODE_PRIVATE);
                                        String tokenLogin = sh.getString("tokenLogin", "");

                                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                                        Call<DeleteBudgetDetail> deleteBudgetDetailCall = service.deleteBudgetDetail(
                                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin);
                                        deleteBudgetDetailCall.enqueue(new Callback<DeleteBudgetDetail>() {
                                            @Override
                                            public void onResponse(Call<DeleteBudgetDetail> call,
                                                                   Response<DeleteBudgetDetail> response) {

                                                progressDialog.dismiss();

                                                if(response.code() == 200) {
                                                    if (response.body() != null) {
                                                        if(response.body().getCode() == 0) {

                                                            dialogInterface.dismiss();
                                                            dialog.dismiss();
                                                            removeAt(position);

                                                        } else {

                                                            SharedPreferences.Editor editor = sh.edit();
                                                            editor.putBoolean("isUserLogin", false);
                                                            editor.apply();

                                                            Intent intent = new Intent(
                                                                    view.getContext(),
                                                                    LoginActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            view.getContext().startActivity(intent);
                                                        }

//                                        String message = response.body().getMessage();
//                        Toast.makeText(DetailDataPanenActivity.this, message,
//                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(view.getContext(),
                                                                "Something went wrong...Please try later!",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(view.getContext(),
                                                            "Something went wrong...Please try later!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DeleteBudgetDetail> call, Throwable t) {
                                                progressDialog.dismiss();
                                                Toast.makeText(view.getContext(),
                                                        "Something went wrong...Please try later!",
                                                        Toast.LENGTH_SHORT).show();
                                                Log.e("Failure", t.toString());
                                            }
                                        });
                                    }
                                }).setNegativeButton("Tidak", null).show();
                    }
                });

                ImageView imgDokumentasi = dialog.findViewById(R.id.img_dokumentasi);

                txtKategori.setText(dataItem.getCategoryVar());
                txtKegiatan.setText(dataItem.getActivityTxt());
                txtLuas.setText(dataItem.getAreaVar());
                txtJumlah.setText(dataItem.getQuantityVar());
                txtSatuan.setText(dataItem.getSatuanVar());

                String hargaRp = NumberFormat.getInstance(Locale.ENGLISH).
                        format(Integer.parseInt(dataItem.getPriceVar()));
                hargaRp = "Rp " + hargaRp;
                txtHarga.setText(hargaRp);

                String totalRp = NumberFormat.getInstance(Locale.ENGLISH).
                        format(Integer.parseInt(dataItem.getTotalPriceVar()));
                totalRp = "Rp " + totalRp;
                txtTotalHarga.setText(totalRp);

                if(!dataItem.getDocumentTxt().equals("")) {
                    Picasso.Builder builder = new Picasso.Builder(
                            context);
                    builder.downloader(new OkHttp3Downloader(
                            context));
                    builder.build().load(dataItem.getDocumentTxt())
                            .error(R.drawable.img_dokumentasi)
                            .into(imgDokumentasi);
                }

                dialog.show();
            }
        });

//        if(position == 0) {
//            // Header Cells. Main Headings appear here
//            holder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtKategori.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtKegiatan.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtLuas.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtJumlah.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtSatuan.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtHarga.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtTotal.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtStatus.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_header_cell_bg);
//
//        }
//        else {
//
//            BudgetDetail dataItem = budgetDetail.get(position - 1);
//
//            holder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtKategori.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtKegiatan.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtLuas.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtJumlah.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtSatuan.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtHarga.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtTotal.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtStatus.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setTextColor(Color.parseColor("#508EF7"));
//            holder.txtAction.setText("Detail");
//            holder.txtAction.setTypeface(null, Typeface.BOLD);
//
//            holder.txtAction.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context,
//                            DetailAnggaranActivity.class);
//                    intent.putExtra("idSeq", dataItem.getIdSeq());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//            });
//
//            holder.txtNo.setText(String.valueOf(position));
//            holder.txtKategori.setText(dataItem.getCategoryVar());
//            holder.txtKegiatan.setText(dataItem.getActivityTxt());
//            holder.txtLuas.setText(dataItem.getAreaVar());
//            holder.txtJumlah.setText(dataItem.getQuantityVar());
//            holder.txtStatus.setText(dataItem.getSatuanVar());
//
//            String hargaRp = NumberFormat.getInstance(Locale.ENGLISH).
//                    format(Integer.parseInt(dataItem.getPriceVar()));
//            holder.txtHarga.setText(hargaRp);
//
//            String totalRp = NumberFormat.getInstance(Locale.ENGLISH).
//                    format(Integer.parseInt(dataItem.getTotalPriceVar()));
//            holder.txtTotal.setText(totalRp);
//
//            holder.txtStatus.setText(dataItem.getStatusNameVar());
//        }
    }

    @Override
    public int getItemCount() {
        return budgetDetail.size();
    }

    public void removeAt(int position) {
        budgetDetail.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, budgetDetail.size());
    }

    public void filterList(List<BudgetDetail> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        budgetDetail = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private final TextView txtKategori, txtKegiatan, txtTotalNominal, txtStatus;
        ImageView btnAction;

//        TextView txtNo, txtKategori, txtKegiatan, txtLuas, txtJumlah, txtSatuan;
//        TextView txtHarga, txtTotal, txtStatus, txtAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnAction = mView.findViewById(R.id.btn_action);

            txtKategori = mView.findViewById(R.id.txt_kategori);
            txtKegiatan = mView.findViewById(R.id.txt_kegiatan);
            txtTotalNominal = mView.findViewById(R.id.txt_total_nominal);
            txtStatus = mView.findViewById(R.id.txt_status);

//            txtNo = mView.findViewById(R.id.txt_no);
//            txtKategori = mView.findViewById(R.id.txt_kategori);
//            txtKegiatan = mView.findViewById(R.id.txt_kegiatan);
//            txtLuas = mView.findViewById(R.id.txt_luas);
//            txtJumlah = mView.findViewById(R.id.txt_jumlah);
//            txtSatuan = mView.findViewById(R.id.txt_satuan);
//            txtHarga = mView.findViewById(R.id.txt_harga);
//            txtTotal = mView.findViewById(R.id.txt_total);
//            txtStatus = mView.findViewById(R.id.txt_status);
//            txtAction = mView.findViewById(R.id.txt_action);

        }
    }
}
