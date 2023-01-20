package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_one.Rating;

import org.w3c.dom.Text;

import java.util.List;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {

    Context context;
    List<Rating> rating;

    public CatatanAdapter(Context context, List<Rating> rating) {
       this.context = context;
       this.rating = rating;
    }

    @NonNull
    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_catatan, parent, false);
        return new CatatanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanAdapter.ViewHolder holder, int position) {

        Rating dataItem = rating.get(position);

        String idRating = "Catatan / Saran  " + String.valueOf(position + 1);
        String saran = ": " + dataItem.getSuggestTxt();
        String penilaian = ": " + dataItem.getRatingTxt();

        holder.txtIdSaran.setText(idRating);
        holder.txtSaran.setText(saran);
        holder.txtPenilaian.setText(penilaian);

    }

    @Override
    public int getItemCount() {
        return rating.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtIdSaran;
        TextView txtSaranTitle;
        TextView txtSaran;
        TextView txtPenilaian;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            txtIdSaran = mView.findViewById(R.id.txt_id_saran);
            txtSaranTitle = mView.findViewById(R.id.txt_saran_title);
            txtSaran = mView.findViewById(R.id.txt_saran);
            txtPenilaian = mView.findViewById(R.id.txt_penilaian);
        }
    }
}
