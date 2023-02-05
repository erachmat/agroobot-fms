package com.example.agroobot_fms.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.example.agroobot_fms.DetailDataPanenActivity;
import com.example.agroobot_fms.EditDataPanenActivity;
import com.example.agroobot_fms.HomeActivity;
import com.example.agroobot_fms.LoginActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.ajukan_data_panen.AjukanDataPanen;
import com.example.agroobot_fms.model.ajukan_data_panen.AjukanPanenBody;
import com.example.agroobot_fms.model.batal_ajukan_panen.BatalAjukanPanen;
import com.example.agroobot_fms.model.batal_ajukan_panen.BatalAjukanPanenBody;
import com.example.agroobot_fms.model.delete_data_panen.DeleteDataPanen;
import com.example.agroobot_fms.model.get_all_data_panen.Datum;
import com.example.agroobot_fms.model.update_rating.UpdateRatingBody;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPanenAdapter extends RecyclerView.Adapter<DataPanenAdapter.ViewHolder>{

    Context context;
    List<Datum> data;

    public DataPanenAdapter(Context context, List<Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DataPanenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_card_data_panen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataPanenAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Datum dataItem = data.get(position);

        holder.txtNamaKomoditas.setText(dataItem.getCommodityNameVar());
        holder.txtLahan.setText(dataItem.getLandCodeVar());

        holder.txtStatus.setText(dataItem.getStatusNameVar());
        switch (dataItem.getStatusNameVar()) {
            case "draft":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_grey);
                break;
            case "approval":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_blue);
                break;
            case "approved":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_green);
                break;
            case "rejected":
                holder.txtStatus.setBackgroundResource(R.drawable.button_rounded_red);
                break;
        }

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context,
//                        DetailDataPanenActivity.class);
//                intent.putExtra("idSeq", dataItem.getIdSeq());
//                context.startActivity(intent);

                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.lyt_dialog_detail_panen);

                TextView txtKomoditas = dialog.findViewById(R.id.txt_komoditas);
                TextView txtLahan = dialog.findViewById(R.id.txt_lahan);
                TextView txtPeriodeTanam = dialog.findViewById(R.id.txt_periode_tanam);
                TextView txtHasilPanen = dialog.findViewById(R.id.txt_hasil_panen);
                TextView txtTglPanen = dialog.findViewById(R.id.txt_tgl_panen);
                TextView txtTglPenjemuran = dialog.findViewById(R.id.txt_tgl_penjemuran);
                TextView txtHasilPenjemuran = dialog.findViewById(R.id.txt_hasil_penjemuran);
                TextView txtTglPenggilingan = dialog.findViewById(R.id.txt_tgl_penggilingan);
                TextView txtHasilPenggilingan = dialog.findViewById(R.id.txt_hasil_penggilingan);

                ImageView imgDokumentasi = dialog.findViewById(R.id.img_dokumentasi);

                ImageView btnClose = dialog.findViewById(R.id.btn_close);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ImageView btnEdit = dialog.findViewById(R.id.btn_edit);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),
                                EditDataPanenActivity.class);
                        intent.putExtra("idSeq", dataItem.getIdSeq());
                        view.getContext().startActivity(intent);
                    }
                });

                ImageView btnDelete = dialog.findViewById(R.id.btn_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref",
                                Context.MODE_PRIVATE);
                        String tokenLogin = sh.getString("tokenLogin", "");

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        Call<DeleteDataPanen> deleteDataPanenCall = service.deleteDataPanen(
                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin);
                        deleteDataPanenCall.enqueue(new Callback<DeleteDataPanen>() {
                            @Override
                            public void onResponse(Call<DeleteDataPanen> call,
                                                   Response<DeleteDataPanen> response) {

                                progressDialog.dismiss();

                                if(response.code() == 200) {
                                    if (response.body() != null) {
                                        if(response.body().getCode() == 0) {

                                            dialog.dismiss();
                                            removeAt(position);

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
                            public void onFailure(Call<DeleteDataPanen> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(view.getContext(),
                                        "Something went wrong...Please try later!",
                                        Toast.LENGTH_SHORT).show();
                                Log.e("Failure", t.toString());
                            }
                        });
                    }
                });

                LinearLayout btnAjukan = dialog.findViewById(R.id.btn_ajukan);
                btnAjukan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref",
                                Context.MODE_PRIVATE);
                        String tokenLogin = sh.getString("tokenLogin", "");
                        String fullnameVar = sh.getString("fullnameVar", "");

                        AjukanPanenBody ajukanPanenBody = new AjukanPanenBody();
                        ajukanPanenBody.setUpdatedByVar(fullnameVar);

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        Call<AjukanDataPanen> ajukanDataPanenCall = service.ajukanPanen(
                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin, ajukanPanenBody);
                        ajukanDataPanenCall.enqueue(new Callback<AjukanDataPanen>() {
                            @Override
                            public void onResponse(Call<AjukanDataPanen> call,
                                                   Response<AjukanDataPanen> response) {

                                progressDialog.dismiss();

                                if(response.code() == 200) {
                                    if (response.body() != null) {
                                        if(response.body().getCode() == 0) {

                                            dialog.dismiss();
                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("viewpager_position", 2);
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
                            public void onFailure(Call<AjukanDataPanen> call, Throwable t) {
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
                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref",
                                Context.MODE_PRIVATE);
                        String tokenLogin = sh.getString("tokenLogin", "");
                        String fullnameVar = sh.getString("fullnameVar", "");

                        BatalAjukanPanenBody batalAjukanPanenBody = new BatalAjukanPanenBody();
                        batalAjukanPanenBody.setUpdatedByVar(fullnameVar);

                        GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
                        Call<BatalAjukanPanen> batalAjukanPanenCall = service.batalAjukanPanen(
                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin, batalAjukanPanenBody);
                        batalAjukanPanenCall.enqueue(new Callback<BatalAjukanPanen>() {
                            @Override
                            public void onResponse(Call<BatalAjukanPanen> call,
                                                   Response<BatalAjukanPanen> response) {

                                progressDialog.dismiss();

                                if(response.code() == 200) {
                                    if (response.body() != null) {
                                        if(response.body().getCode() == 0) {

                                            dialog.dismiss();
                                            Intent intent = new Intent(
                                                    view.getContext(),
                                                    HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("viewpager_position", 2);
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
                            public void onFailure(Call<BatalAjukanPanen> call, Throwable t) {
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
                            view.getContext());
                    builder.downloader(new OkHttp3Downloader(
                            view.getContext()));
                    builder.build().load(dataItem.getDocumentTxt().get(0))
                            .error(R.drawable.img_dokumentasi)
                            .into(imgDokumentasi);
                }

                dialog.show();
            }
        });

//        if(position == 0) {
//            // Header Cells. Main Headings appear here
//            holder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtKomoditas.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtLahan.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtTglPanen.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtHasilPanen.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtStatusApproval.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_header_cell_bg);
//
//            holder.txtNo.setText("No");
//            holder.txtKomoditas.setText("Komoditas");
//            holder.txtLahan.setText("Lahan");
//            holder.txtPeriodeTanam.setText("Periode Tanam");
//            holder.txtTglPanen.setText("Tanggal Panen");
//            holder.txtHasilPanen.setText("Hasil Panen (Kg)");
//            holder.txtStatusApproval.setText("Status Approval");
//            holder.txtStatusApproval.setText("Action");
//        }
//        else {
//
//            Datum dataItem = data.get(position - 1);
//
//            holder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtKomoditas.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtLahan.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtTglPanen.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtHasilPanen.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtStatusApproval.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setTextColor(Color.parseColor("#508EF7"));
//            holder.txtAction.setText("Detail");
//            holder.txtAction.setTypeface(null, Typeface.BOLD);
//
//            holder.txtAction.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context,
//                            DetailDataPanenActivity.class);
//                    intent.putExtra("idSeq", dataItem.getIdSeq());
//                    context.startActivity(intent);
//                }
//            });
//
//            holder.txtNo.setText(String.valueOf(position));
//            holder.txtKomoditas.setText(dataItem.getCommodityNameVar());
//            holder.txtLahan.setText(dataItem.getLandCodeVar());
//
//            String tglPanen = dataItem.getHarvestOnDte().substring(0, 10);
//
//            holder.txtPeriodeTanam.setText(dataItem.getPeriodPlantTxt());
//            holder.txtTglPanen.setText(tglPanen);
//            holder.txtHasilPanen.setText(String.valueOf(dataItem.getHarvestFlo()));
//            holder.txtStatusApproval.setText(dataItem.getStatusNameVar());
//
//        }
//
//        holder.txtAction.setVisibility(View.VISIBLE);
//        holder.btnAction.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
//        return data.size() + 1;
    }

    public void filterList(List<Datum> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        data = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnAction;
        TextView txtNamaKomoditas, txtLahan, txtStatus;

//        TextView txtNo;
//        TextView txtKomoditas;
//        TextView txtLahan;
//        TextView txtPeriodeTanam;
//        TextView txtTglPanen;
//        TextView txtHasilPanen;
//        TextView txtStatusApproval;
//        TextView txtAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnAction = mView.findViewById(R.id.btn_action);

            txtNamaKomoditas = mView.findViewById(R.id.txt_nama_komoditas);
            txtLahan = mView.findViewById(R.id.txt_lahan);
            txtStatus = mView.findViewById(R.id.txt_status);
//
//            txtNo = mView.findViewById(R.id.txtNo);
//            txtKomoditas = mView.findViewById(R.id.txtKomoditas);
//            txtLahan = mView.findViewById(R.id.txtLahan);
//            txtPeriodeTanam = mView.findViewById(R.id.txtPeriodeTanam);
//            txtTglPanen = mView.findViewById(R.id.txtTglPanen);
//            txtHasilPanen = mView.findViewById(R.id.txtHasilPanen);
//            txtStatusApproval = mView.findViewById(R.id.txtStatusApproval);
//            txtAction = mView.findViewById(R.id.txtAction);

        }
    }
}
