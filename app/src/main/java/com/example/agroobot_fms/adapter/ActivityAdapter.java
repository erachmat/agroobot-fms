package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_one.Activity;
import com.example.agroobot_fms.model.get_one.Data;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    Context context;
    List<Activity> activity;

    public ActivityAdapter(Context context, List<Activity> activity) {
       this.context = context;
       this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Activity dataItem = activity.get(position);

        String idActivity = "Aktivitas " + String.valueOf(position + 1);
        String namaBahan = ": " + dataItem.getMaterialTxt();
        String dosis = ": " + dataItem.getDoseTxt();
        String jumlahHst = ": " + String.valueOf(dataItem.getJumlahTxt());
        String satuanHst = ": " + dataItem.getSatuanTxt();

        holder.txtActivity.setText(idActivity);
        holder.txtActivityTitle.setText(dataItem.getActivityTxt());
        holder.txtNamaBahan.setText(namaBahan);
        holder.txtDosis.setText(dosis);
        holder.txtJumlahHst.setText(jumlahHst);
        holder.txtSatuanHst.setText(satuanHst);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormActivity()) {

                }
            }
        });
    }

    private boolean checkFormActivity() {
        return true;
    }

    @Override
    public int getItemCount() {
        return activity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnEdit, btnDelete;

        TextView txtActivity;
        TextView txtActivityTitle;
        TextView txtNamaBahan;
        TextView txtDosis;
        TextView txtJumlahHst;
        TextView txtSatuanHst;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnEdit = mView.findViewById(R.id.btn_edit);
            btnDelete = mView.findViewById(R.id.btn_delete);

            txtActivity = mView.findViewById(R.id.txt_activity);
            txtActivityTitle = mView.findViewById(R.id.txt_activity_title);
            txtNamaBahan = mView.findViewById(R.id.txt_nama_bahan);
            txtDosis = mView.findViewById(R.id.txt_dosis);
            txtJumlahHst = mView.findViewById(R.id.txt_jumlah_hst);
            txtSatuanHst = mView.findViewById(R.id.txt_satuan_hst);
        }
    }
}
