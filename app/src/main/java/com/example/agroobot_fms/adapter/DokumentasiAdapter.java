package com.example.agroobot_fms.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityBody;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityResponse;
import com.example.agroobot_fms.model.delete_documentation.DeleteDocumentation;
import com.example.agroobot_fms.model.delete_documentation.DeleteDocumentationBody;
import com.example.agroobot_fms.model.get_one.Documentation;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DokumentasiAdapter extends RecyclerView.Adapter<DokumentasiAdapter.ViewHolder> {

    int weight = 2; //number of parts in the recycler view.

    Context context;
    List<Documentation> documentation;
    String tokenLogin;
    String idPetani;
    String idLahan;
    String idPeriode;

    public DokumentasiAdapter(Context context, List<Documentation> documentation,
                              String tokenLogin, String idPetani, String idLahan, String idPeriode) {
        this.context = context;
        this.documentation = documentation;
        this.tokenLogin = tokenLogin;
        this.idPetani = idPetani;
        this.idLahan = idLahan;
        this.idPeriode = idPeriode;
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Hapus Dokumentasi")
                        .setMessage("Anda yakin menghapus data ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ProgressDialog progressDialog = new ProgressDialog(
                                        view.getContext());
                                progressDialog.setMessage("Loading....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                DeleteDocumentationBody deleteDocumentationBody =
                                        new DeleteDocumentationBody();
                                deleteDocumentationBody.setUserIdInt(idPetani);
                                deleteDocumentationBody.setLandCodeVar(idLahan);
                                deleteDocumentationBody.setPeriodPlantTxt(idPeriode);

                                GetService service = ApiClient.getRetrofitInstance()
                                        .create(GetService.class);
                                Call<DeleteDocumentation> deleteDocumentationCall =
                                        service.deleteDocumentation(
                                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin,
                                                deleteDocumentationBody);
                                deleteDocumentationCall.enqueue(new Callback<DeleteDocumentation>() {
                                    @Override
                                    public void onResponse(Call<DeleteDocumentation> call,
                                                           Response<DeleteDocumentation> response) {

                                        progressDialog.dismiss();

                                        if(response.code() == 200) {
                                            if (response.body() != null) {
                                                if(response.body().getCode() == 0) {
                                                    Toast.makeText(view.getContext(),
                                                            "Silahkan refresh list dokumentasi!",
                                                            Toast.LENGTH_SHORT).show();
                                                }

//                                                String message = response.body().getMessage();
//                                                Toast.makeText(view.getContext(), message,
//                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(view.getContext(),
                                                        "Something went wrong...Please try later!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(view.getContext(),
                                                    "Something went wrong...Please try later!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DeleteDocumentation> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(view.getContext(),
                                                "Something went wrong...Please try later!",
                                                Toast.LENGTH_SHORT).show();
                                        Log.e("Failure", t.toString());
                                    }
                                });

                            }
                        }).setNegativeButton("Tidak", null).show();
            }
        });
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
