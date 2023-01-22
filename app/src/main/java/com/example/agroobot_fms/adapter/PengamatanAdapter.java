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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.delete_observation.DeleteObservationBody;
import com.example.agroobot_fms.model.delete_observation.DeleteObservationResponse;
import com.example.agroobot_fms.model.get_one.Observation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengamatanAdapter extends RecyclerView.Adapter<PengamatanAdapter.ViewHolder> {

    Context context;
    List<Observation> observation;
    String tokenLogin;
    String idPetani;
    String idLahan;
    String idPeriode;

    public PengamatanAdapter(Context context, List<Observation> observation,
                             String tokenLogin, String idPetani, String idLahan,
                             String idPeriode) {
       this.context = context;
       this.observation = observation;
       this.tokenLogin = tokenLogin;
       this.idPetani = idPetani;
       this.idLahan = idLahan;
       this.idPeriode = idPeriode;
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Hapus Pengamatan")
                        .setMessage("Anda yakin menghapus data ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ProgressDialog progressDialog = new ProgressDialog(
                                        view.getContext());
                                progressDialog.setMessage("Loading....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                DeleteObservationBody deleteObservationBody =
                                        new DeleteObservationBody();
                                deleteObservationBody.setUserIdInt(idPetani);
                                deleteObservationBody.setLandCodeVar(idLahan);
                                deleteObservationBody.setPeriodPlantTxt(idPeriode);

                                GetService service = ApiClient.getRetrofitInstance()
                                        .create(GetService.class);
                                Call<DeleteObservationResponse> deleteObservationResponseCall =
                                        service.deleteObservation(
                                                Integer.parseInt(dataItem.getIdSeq()), tokenLogin,
                                                deleteObservationBody);
                                deleteObservationResponseCall.enqueue(new Callback<DeleteObservationResponse>() {
                                    @Override
                                    public void onResponse(Call<DeleteObservationResponse> call,
                                                           Response<DeleteObservationResponse> response) {
                                        
                                        progressDialog.dismiss();

                                        if(response.code() == 200) {
                                            if (response.body() != null) {
                                                if(response.body().getCode() == 0) {
                                                    Toast.makeText(view.getContext(),
                                                            "Silahkan refresh list pengamatan!",
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
                                    public void onFailure(Call<DeleteObservationResponse> call,
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
