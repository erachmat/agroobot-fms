package com.example.agroobot_fms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;

public class PengamatanAdapter extends RecyclerView.Adapter<PengamatanAdapter.ViewHolder> {
    @NonNull
    @Override
    public PengamatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_pengamatan, parent, false);
        return new PengamatanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengamatanAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
