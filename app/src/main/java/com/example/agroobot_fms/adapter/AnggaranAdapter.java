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

import com.example.agroobot_fms.DetailAnggaranPetaniActivity;
import com.example.agroobot_fms.DetailDataPanenActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_all_budget_plan.Datum;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AnggaranAdapter extends RecyclerView.Adapter<AnggaranAdapter.ViewHolder> {

    Context context;
    List<Datum> data;

    public AnggaranAdapter(Context context, List<Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AnggaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_card_anggaran, parent, false);
        return new AnggaranAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggaranAdapter.ViewHolder holder, int position) {

        Datum dataItem = data.get(position);

        holder.txtNamaPetani.setText(dataItem.getFullnameVar());

        String infoLahan = "[" + dataItem.getLandCodeVar() + "] " + dataItem.getLandNameVar();
        holder.txtLahan.setText(infoLahan);

        String budgetPlanRp = NumberFormat.getInstance(Locale.ENGLISH).
                    format(Integer.parseInt(dataItem.getBudgetPlanVar()));
        budgetPlanRp = "Rp " + budgetPlanRp;
        holder.txtNominalBudget.setText(budgetPlanRp);

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,
                            DetailAnggaranPetaniActivity.class);
                    intent.putExtra("idSeq", dataItem.getIdSeq());
                    context.startActivity(intent);
                }
            });

//        if(position == 0) {
//            // Header Cells. Main Headings appear here
//            holder.txtNo.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtKomoditas.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtLahan.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtBudgetPlan.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtPetani.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_header_cell_bg);
//
//            holder.txtNo.setText("No");
//            holder.txtKomoditas.setText("Komoditas");
//            holder.txtPeriodeTanam.setText("Periode Tanam");
//            holder.txtPetani.setText("Petani");
//            holder.txtLahan.setText("Lahan");
//            holder.txtBudgetPlan.setText("Budget Plan");
//            holder.txtAction.setText("Action");
//
//        }
//        else {
//
//            Datum dataItem = data.get(position - 1);
//
//            holder.txtNo.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtKomoditas.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtLahan.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtPeriodeTanam.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtPetani.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtBudgetPlan.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtAction.setTextColor(Color.parseColor("#508EF7"));
//            holder.txtAction.setText("Detail");
//            holder.txtAction.setTypeface(null, Typeface.BOLD);
//
//            holder.txtAction.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context,
//                            DetailAnggaranPetaniActivity.class);
//                    intent.putExtra("idSeq", dataItem.getIdSeq());
//                    context.startActivity(intent);
//                }
//            });
//
//            holder.txtNo.setText(String.valueOf(position));
//            holder.txtKomoditas.setText(dataItem.getCommodityNameVar());
//
//            String infoLahan = "[" + dataItem.getLandCodeVar() + "] " + dataItem.getLandNameVar();
//
//            holder.txtPeriodeTanam.setText(dataItem.getPeriodPlantTxt());
//            holder.txtPetani.setText(dataItem.getFullnameVar());
//            holder.txtLahan.setText(infoLahan);
//
//            String budgetPlanRp = NumberFormat.getInstance(Locale.ENGLISH).
//                    format(Integer.parseInt(dataItem.getBudgetPlanVar()));
//
//            holder.txtBudgetPlan.setText(budgetPlanRp);
//        }
//
//        holder.txtAction.setVisibility(View.VISIBLE);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private final TextView txtNamaPetani, txtLahan, txtNominalBudget;
        ImageView btnAction;

//        TextView txtNo;
//        TextView txtPetani;
//        TextView txtLahan;
//        TextView txtKomoditas;
//        TextView txtPeriodeTanam;
//        TextView txtBudgetPlan;
//        TextView txtAction;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnAction = mView.findViewById(R.id.btn_action);

            txtNamaPetani = mView.findViewById(R.id.txt_nama_petani);
            txtLahan = mView.findViewById(R.id.txt_lahan);
            txtNominalBudget = mView.findViewById(R.id.txt_nominal_budget);

//            txtNo = mView.findViewById(R.id.txt_no);
//            txtPetani = mView.findViewById(R.id.txt_petani);
//            txtLahan = mView.findViewById(R.id.txt_lahan);
//            txtKomoditas = mView.findViewById(R.id.txt_komoditas);
//            txtPeriodeTanam = mView.findViewById(R.id.txt_periode_tanam);
//            txtBudgetPlan = mView.findViewById(R.id.txt_budget_plan);
//            txtAction = mView.findViewById(R.id.txt_action);
        }
    }
}
