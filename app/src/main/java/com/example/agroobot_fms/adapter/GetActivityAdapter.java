package com.example.agroobot_fms.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_activity_dashboard.Datum;
import com.example.agroobot_fms.model.get_one.Data;

import java.util.List;

public class GetActivityAdapter extends RecyclerView.Adapter<GetActivityAdapter.ViewHolder> {

    Context context;
    List<Datum> listData;

    public GetActivityAdapter(Context context, List<Datum> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public GetActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_card_get_activity, parent, false);
        return new GetActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetActivityAdapter.ViewHolder holder, int position) {

        Datum dataItem = listData.get(position);

        holder.txtAktivitas.setText(dataItem.getActivityTxt());
        holder.txtWaktu.setText(dataItem.getTimeTxt());
        holder.txtTanggal.setText(dataItem.getTimeCalenderDte().substring(0, 10));

        String idActivity = "Aktivitas " + (position + 1);
        holder.txtActivity.setText(idActivity);

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.lyt_dialog_get_activity);

                TextView txtAktivitas = dialog.findViewById(R.id.txt_aktivitas);
                TextView txtWaktu = dialog.findViewById(R.id.txt_waktu);
                TextView txtTgl = dialog.findViewById(R.id.txt_tgl);
                TextView txtKomoditas = dialog.findViewById(R.id.txt_komoditas);
                TextView txtMetodeTanam = dialog.findViewById(R.id.txt_metode_tanam);
                TextView txtLahan = dialog.findViewById(R.id.txt_lahan);
                TextView txtPeriode = dialog.findViewById(R.id.txt_periode);
                TextView txtPetani = dialog.findViewById(R.id.txt_petani);
                TextView txtPendamping = dialog.findViewById(R.id.txt_pendamping);

                ImageView btnClose = dialog.findViewById(R.id.btn_close);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                txtAktivitas.setText(dataItem.getActivityTxt());
                txtWaktu.setText(dataItem.getTimeTxt());
                txtTgl.setText(dataItem.getTimeCalenderDte().substring(0, 10));
                txtKomoditas.setText(dataItem.getCommodityName());
                txtMetodeTanam.setText(dataItem.getNamePlanting());
                txtLahan.setText(dataItem.getLandNameVar());
                txtPeriode.setText(dataItem.getPeriodPlantTxt());
                txtPetani.setText(dataItem.getFullnameVar());
                txtPendamping.setText(dataItem.getFieldAssistantNameVar());

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnAction;
        TextView txtAktivitas, txtWaktu, txtTanggal;
        TextView txtActivity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnAction = mView.findViewById(R.id.btn_action);

            txtActivity = mView.findViewById(R.id.txt_activity);
            txtAktivitas = mView.findViewById(R.id.txt_aktivitas);
            txtWaktu = mView.findViewById(R.id.txt_waktu);
            txtTanggal = mView.findViewById(R.id.txt_tgl);
        }
    }
}
