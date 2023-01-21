package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.AddDataPanenActivity;
import com.example.agroobot_fms.DetailDataPanenActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_all_data_panen.Datum;

import java.util.List;

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
        View view = inflater.inflate(R.layout.lyt_list_data_panen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataPanenAdapter.ViewHolder holder, int position) {

        if(position == 0) {
            // Header Cells. Main Headings appear here
            holder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtKomoditas.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtLahan.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtTglPanen.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtHasilPanen.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtStatusApproval.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.txtAction.setBackgroundResource(R.drawable.table_header_cell_bg);

        } else {

            Datum dataItem = data.get(position - 1);

            holder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtKomoditas.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtLahan.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtTglPanen.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtHasilPanen.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtStatusApproval.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtAction.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.txtAction.setTextColor(Color.parseColor("#508EF7"));
            holder.txtAction.setText("Detail");
            holder.txtAction.setTypeface(null, Typeface.BOLD);

            holder.txtAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,
                            DetailDataPanenActivity.class);
                    intent.putExtra("idSeq", dataItem.getIdSeq());
                    context.startActivity(intent);
                }
            });

            holder.txtNo.setText(String.valueOf(position));
            holder.txtKomoditas.setText(dataItem.getCommodityNameVar());
            holder.txtLahan.setText(dataItem.getLandCodeVar());

            String tglPanen = dataItem.getHarvestOnDte().substring(0, 10);

            holder.txtPeriodeTanam.setText(dataItem.getPeriodPlantTxt());
            holder.txtTglPanen.setText(tglPanen);
            holder.txtHasilPanen.setText(String.valueOf(dataItem.getHarvestFlo()));
            holder.txtStatusApproval.setText(dataItem.getStatusNameVar());

        }

        holder.txtAction.setVisibility(View.VISIBLE);
        holder.btnAction.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnAction;

        TextView txtNo;
        TextView txtKomoditas;
        TextView txtLahan;
        TextView txtPeriodeTanam;
        TextView txtTglPanen;
        TextView txtHasilPanen;
        TextView txtStatusApproval;
        TextView txtAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnAction = mView.findViewById(R.id.btn_action);

            txtNo = mView.findViewById(R.id.txtNo);
            txtKomoditas = mView.findViewById(R.id.txtKomoditas);
            txtLahan = mView.findViewById(R.id.txtLahan);
            txtPeriodeTanam = mView.findViewById(R.id.txtPeriodeTanam);
            txtTglPanen = mView.findViewById(R.id.txtTglPanen);
            txtHasilPanen = mView.findViewById(R.id.txtHasilPanen);
            txtStatusApproval = mView.findViewById(R.id.txtStatusApproval);
            txtAction = mView.findViewById(R.id.txtAction);

        }
    }
}
