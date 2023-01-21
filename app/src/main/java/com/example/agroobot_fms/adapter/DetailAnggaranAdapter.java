package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.DetailAnggaranActivity;
import com.example.agroobot_fms.DetailAnggaranPetaniActivity;
import com.example.agroobot_fms.DetailDataPanenActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_one_budget_plan.BudgetDetail;
import com.example.agroobot_fms.model.get_one_budget_plan.Data;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailAnggaranAdapter extends RecyclerView.Adapter<DetailAnggaranAdapter.ViewHolder> {

    Context context;
    List<BudgetDetail> budgetDetail;

    public DetailAnggaranAdapter(Context context, List<BudgetDetail> budgetDetail) {
       this.context = context;
       this.budgetDetail = budgetDetail;
    }

    @NonNull
    @Override
    public DetailAnggaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_detail_anggaran,
                parent, false);
        return new DetailAnggaranAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAnggaranAdapter.ViewHolder holder, int position) {

        if(position == 0) {
            // Header Cells. Main Headings appear here
            holder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtKategori.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtKegiatan.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtLuas.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtJumlah.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtSatuan.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtHarga.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtTotal.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtStatus.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtAction.setBackgroundResource(R.drawable.table_header_cell_bg);

        } else {

            BudgetDetail dataItem = budgetDetail.get(position - 1);

            holder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtKategori.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtKegiatan.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtLuas.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtJumlah.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtSatuan.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtHarga.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtTotal.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtStatus.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtAction.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtAction.setTextColor(Color.parseColor("#508EF7"));
            holder.txtAction.setText("Detail");
            holder.txtAction.setTypeface(null, Typeface.BOLD);

            holder.txtAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,
                            DetailAnggaranActivity.class);
                    intent.putExtra("idSeq", dataItem.getIdSeq());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.txtNo.setText(String.valueOf(position));
            holder.txtKategori.setText(dataItem.getCategoryVar());
            holder.txtKegiatan.setText(dataItem.getActivityTxt());
            holder.txtLuas.setText(dataItem.getAreaVar());
            holder.txtJumlah.setText(dataItem.getQuantityVar());
            holder.txtStatus.setText(dataItem.getSatuanVar());

            String hargaRp = NumberFormat.getInstance(Locale.ENGLISH).
                    format(Integer.parseInt(dataItem.getPriceVar()));
            holder.txtHarga.setText(hargaRp);

            String totalRp = NumberFormat.getInstance(Locale.ENGLISH).
                    format(Integer.parseInt(dataItem.getTotalPriceVar()));
            holder.txtTotal.setText(totalRp);

            holder.txtStatus.setText(dataItem.getStatusNameVar());
        }
    }

    @Override
    public int getItemCount() {
        return budgetDetail.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtNo, txtKategori, txtKegiatan, txtLuas, txtJumlah, txtSatuan;
        TextView txtHarga, txtTotal, txtStatus, txtAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            txtNo = mView.findViewById(R.id.txt_no);
            txtKategori = mView.findViewById(R.id.txt_kategori);
            txtKegiatan = mView.findViewById(R.id.txt_kegiatan);
            txtLuas = mView.findViewById(R.id.txt_luas);
            txtJumlah = mView.findViewById(R.id.txt_jumlah);
            txtSatuan = mView.findViewById(R.id.txt_satuan);
            txtHarga = mView.findViewById(R.id.txt_harga);
            txtTotal = mView.findViewById(R.id.txt_total);
            txtStatus = mView.findViewById(R.id.txt_status);
            txtAction = mView.findViewById(R.id.txt_action);

        }
    }
}
