package com.example.agroobot_fms.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.EditAktivitasActivity;
import com.example.agroobot_fms.EditSaranActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityBody;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityResponse;
import com.example.agroobot_fms.model.delete_rating.DeleteRatingBody;
import com.example.agroobot_fms.model.delete_rating.DeleteRatingResponse;
import com.example.agroobot_fms.model.get_one.Documentation;
import com.example.agroobot_fms.model.get_one.Rating;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {

    Context context;
    List<Rating> rating;
    String tokenLogin;
    String idPetani;
    String idLahan;
    String idPeriode;

    public CatatanAdapter(Context context, List<Rating> rating, String tokenLogin,
                          String idPetani, String idLahan, String idPeriode) {
       this.context = context;
       this.rating = rating;
       this.tokenLogin = tokenLogin;
       this.idPetani = idPetani;
       this.idLahan = idLahan;
       this.idPeriode = idPeriode;
    }

    @NonNull
    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_catatan, parent, false);
        return new CatatanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Rating dataItem = rating.get(position);

        String idRating = "Catatan / Saran  " + (position + 1);
        String saran = ": " + dataItem.getSuggestTxt();
        String penilaian = ": " + dataItem.getRatingTxt();

        holder.txtIdSaran.setText(idRating);
        holder.txtSaran.setText(saran);
        holder.txtPenilaian.setText(penilaian);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dataJson = new Gson().toJson(dataItem);

                Intent intent = new Intent(context,
                        EditSaranActivity.class);
                intent.putExtra("dataJson", dataJson);
                intent.putExtra("idPetani", idPetani);
                intent.putExtra("idLahan", idLahan);
                intent.putExtra("idPeriode", idPeriode);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

//                Log.e("idPetani", String.valueOf(idPetani));
//                Log.e("idLahan", String.valueOf(idLahan));
//                Log.e("idPeriode", String.valueOf(idPeriode));
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Hapus Penilaian")
                        .setMessage("Anda yakin menghapus data ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ProgressDialog progressDialog = new ProgressDialog(
                                        view.getContext());
                                progressDialog.setMessage("Loading....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                DeleteRatingBody deleteRatingBody = new DeleteRatingBody();
                                deleteRatingBody.setUserIdInt(idPetani);
                                deleteRatingBody.setLandCodeVar(idLahan);
                                deleteRatingBody.setPeriodPlantTxt(idPeriode);

                                GetService service = ApiClient.getRetrofitInstance()
                                        .create(GetService.class);
                                Call<DeleteRatingResponse> deleteRatingResponseCall =
                                        service.deleteRating(
                                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin,
                                                deleteRatingBody);
                                deleteRatingResponseCall.enqueue(new Callback<DeleteRatingResponse>() {
                                    @Override
                                    public void onResponse(Call<DeleteRatingResponse> call,
                                                           Response<DeleteRatingResponse> response) {

                                        progressDialog.dismiss();

                                        if(response.code() == 200) {
                                            if (response.body() != null) {
                                                if(response.body().getCode() == 0) {

                                                    removeAt(position);

//                                                    Toast.makeText(view.getContext(),
//                                                            "Silahkan refresh list penilaian!",
//                                                            Toast.LENGTH_SHORT).show();
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
                                    public void onFailure(Call<DeleteRatingResponse> call,
                                                          Throwable t) {
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
        return rating.size();
    }

    public void removeAt(int position) {
        rating.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, rating.size());
    }

    public void filterList(List<Rating> filteredRating) {
        // below line is to add our filtered
        // list in our course array list.
        rating = filteredRating;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnEdit, btnDelete;

        TextView txtIdSaran;
        TextView txtSaranTitle;
        TextView txtSaran;
        TextView txtPenilaian;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnEdit = mView.findViewById(R.id.btn_edit);
            btnDelete = mView.findViewById(R.id.btn_delete);

            txtIdSaran = mView.findViewById(R.id.txt_id_saran);
            txtSaranTitle = mView.findViewById(R.id.txt_saran_title);
            txtSaran = mView.findViewById(R.id.txt_saran);
            txtPenilaian = mView.findViewById(R.id.txt_penilaian);
        }
    }
}
