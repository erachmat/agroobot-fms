package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_one.Activity;
import com.example.agroobot_fms.model.get_one.Observation;

import org.w3c.dom.Text;

import java.util.List;

public class PengamatanAdapter extends RecyclerView.Adapter<PengamatanAdapter.ViewHolder> {

    Context context;
    List<Observation> observation;

    public PengamatanAdapter(Context context, List<Observation> observation) {
       this.context = context;
       this.observation = observation;
    }

    @NonNull
    @Override
    public PengamatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_pengamatan, parent, false);
        return new PengamatanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengamatanAdapter.ViewHolder holder, int position) {

        Observation dataItem = observation.get(position);

        String idObservation = "Pengamatan " + String.valueOf(position + 1);
        String kondisiDaun = ": " + dataItem.getLeafConditionTxt();
        String kondisiTanah = ": " + dataItem.getLandConditionTxt();
        String kondisiPengairan = ": " + dataItem.getWateringConditionTxt();
        String kondisiAnakan = ": " + dataItem.getPuppiesConditionTxt();
        String kondisiBulir = ": " + dataItem.getGrainConditionTxt();
        String kondisiHama = ": " + dataItem.getHamaTxt();
        String catatan = ": " + dataItem.getExampleObservationTxt();

        holder.txtPengamatan.setText(idObservation);
        holder.txtKondisiDaun.setText(kondisiDaun);
        holder.txtKondisiTanah.setText(kondisiTanah);
        holder.txtKondisiPengairan.setText(kondisiPengairan);
        holder.txtKondisiAnakan.setText(kondisiAnakan);
        holder.txtKondisiBulir.setText(kondisiBulir);
        holder.txtSeranganHama.setText(kondisiHama);
        holder.txtCatatan.setText(catatan);
    }

    @Override
    public int getItemCount() {
        return observation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnEdit, btnDelete;

        TextView txtPengamatan;
        TextView txtKondisiDaun;
        TextView txtKondisiTanah;
        TextView txtKondisiPengairan;
        TextView txtKondisiAnakan;
        TextView txtKondisiBulir;
        TextView txtSeranganHama;
        TextView txtCatatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            txtPengamatan = mView.findViewById(R.id.txt_pengamatan);
            txtKondisiDaun = mView.findViewById(R.id.txt_kondisi_daun);
            txtKondisiTanah = mView.findViewById(R.id.txt_kondisi_tanah);
            txtKondisiPengairan = mView.findViewById(R.id.txt_kondisi_pengairan);
            txtKondisiAnakan = mView.findViewById(R.id.txt_kondisi_anakan);
            txtKondisiBulir = mView.findViewById(R.id.txt_kondisi_bulir);
            txtSeranganHama = mView.findViewById(R.id.txt_serangan_hama);
            txtCatatan = mView.findViewById(R.id.txt_catatan);

            btnEdit = mView.findViewById(R.id.btn_edit);
            btnDelete = mView.findViewById(R.id.btn_delete);
        }
    }
}
