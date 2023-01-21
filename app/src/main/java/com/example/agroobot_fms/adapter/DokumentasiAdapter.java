package com.example.agroobot_fms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.model.get_one.Documentation;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DokumentasiAdapter extends RecyclerView.Adapter<DokumentasiAdapter.ViewHolder> {

    int weight = 2; //number of parts in the recycler view.

    Context context;
    List<Documentation> documentation;

    public DokumentasiAdapter(Context context, List<Documentation> documentation) {
        this.context = context;
        this.documentation = documentation;
    }

    @NonNull
    @Override
    public DokumentasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_dokumentasi, parent, false);
        return new DokumentasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DokumentasiAdapter.ViewHolder holder, int position) {

        Documentation dataItem = documentation.get(position);

        if(dataItem.getDocumentTxt().size() != 0) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(dataItem.getDocumentTxt().get(0))
                    .error(R.drawable.img_dokumentasi)
                    .into(holder.imgDokumentasi);
        }
    }

    @Override
    public int getItemCount() {
        return documentation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnEdit, btnDelete;

        RoundedImageView imgDokumentasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            imgDokumentasi = mView.findViewById(R.id.img_dokumentasi);

            btnEdit = mView.findViewById(R.id.btn_edit);
            btnDelete = mView.findViewById(R.id.btn_delete);
        }
    }
}
