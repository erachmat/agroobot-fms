package com.example.agroobot_fms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {
    @NonNull
    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_catatan, parent, false);
        return new CatatanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
