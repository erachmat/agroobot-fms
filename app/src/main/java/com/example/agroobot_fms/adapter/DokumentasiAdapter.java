package com.example.agroobot_fms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;

public class DokumentasiAdapter extends RecyclerView.Adapter<DokumentasiAdapter.ViewHolder> {

    int weight = 2; //number of parts in the recycler view.

    @NonNull
    @Override
    public DokumentasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_dokumentasi, parent, false);
        return new DokumentasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DokumentasiAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
