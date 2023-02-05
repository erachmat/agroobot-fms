package com.example.agroobot_fms.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroobot_fms.DetailDataPanenActivity;
import com.example.agroobot_fms.EditAktivitasActivity;
import com.example.agroobot_fms.FormAddActivity;
import com.example.agroobot_fms.LoginActivity;
import com.example.agroobot_fms.R;
import com.example.agroobot_fms.api.ApiClient;
import com.example.agroobot_fms.api.GetService;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityBody;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityResponse;
import com.example.agroobot_fms.model.get_one.Activity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    Context context;
    List<Activity> activity;
    String tokenLogin;
    String idPetani;
    String idLahan;
    String idPeriode;

    public ActivityAdapter(Context context, List<Activity> activity, String tokenLogin,
                           String idPetani, String idLahan, String idPeriode) {
       this.context = context;
       this.activity = activity;
       this.tokenLogin = tokenLogin;
       this.idPetani = idPetani;
       this.idLahan = idLahan;
       this.idPeriode = idPeriode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lyt_list_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Activity dataItem = activity.get(position);

        String idActivity = "Aktivitas " + (position + 1);
        String namaBahan = ": " + dataItem.getMaterialTxt();
        String dosis = ": " + dataItem.getDoseTxt();
        String jumlahHst = ": " + String.valueOf(dataItem.getJumlahTxt());
        String satuanHst = ": " + dataItem.getSatuanTxt();

        holder.txtActivity.setText(idActivity);
        holder.txtActivityTitle.setText(dataItem.getActivityTxt());
        holder.txtNamaBahan.setText(namaBahan);
        holder.txtDosis.setText(dosis);
        holder.txtJumlahHst.setText(jumlahHst);
        holder.txtSatuanHst.setText(satuanHst);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dataJson = new Gson().toJson(dataItem);

                Intent intent = new Intent(context,
                        EditAktivitasActivity.class);
                intent.putExtra("dataJson", dataJson);
                intent.putExtra("idPetani", idPetani);
                intent.putExtra("idLahan", idLahan);
                intent.putExtra("idPeriode", idPeriode);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Hapus Activity")
                        .setMessage("Anda yakin menghapus data ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ProgressDialog progressDialog = new ProgressDialog(
                                        view.getContext());
                                progressDialog.setMessage("Loading....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                DeleteActivityBody deleteActivityBody = new DeleteActivityBody();
                                deleteActivityBody.setUserIdInt(idPetani);
                                deleteActivityBody.setLandCodeVar(idLahan);
                                deleteActivityBody.setPeriodPlantTxt(idPeriode);

                                GetService service = ApiClient.getRetrofitInstance()
                                        .create(GetService.class);
                                Call<DeleteActivityResponse> deleteActivityResponseCall =
                                        service.deleteActivity(
                                        Integer.parseInt(dataItem.getIdSeq()), tokenLogin,
                                                deleteActivityBody);
                                deleteActivityResponseCall.enqueue(new Callback<DeleteActivityResponse>() {
                                    @Override
                                    public void onResponse(Call<DeleteActivityResponse> call,
                                                           Response<DeleteActivityResponse> response) {

                                        progressDialog.dismiss();

                                        if(response.code() == 200) {
                                            if (response.body() != null) {
                                                if(response.body().getCode() == 0) {

                                                    removeAt(position);


//                                                    Toast.makeText(view.getContext(),
//                                                            "Silahkan refresh list activity!",
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
                                    public void onFailure(Call<DeleteActivityResponse> call,
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

    private boolean checkFormActivity() {
        return true;
    }

    @Override
    public int getItemCount() {
        return activity.size();
    }

    public void removeAt(int position) {
        activity.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, activity.size());
    }

    public void filterList(List<Activity> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        activity = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView btnEdit, btnDelete;

        TextView txtActivity;
        TextView txtActivityTitle;
        TextView txtNamaBahan;
        TextView txtDosis;
        TextView txtJumlahHst;
        TextView txtSatuanHst;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            btnEdit = mView.findViewById(R.id.btn_edit);
            btnDelete = mView.findViewById(R.id.btn_delete);

            txtActivity = mView.findViewById(R.id.txt_activity);
            txtActivityTitle = mView.findViewById(R.id.txt_activity_title);
            txtNamaBahan = mView.findViewById(R.id.txt_nama_bahan);
            txtDosis = mView.findViewById(R.id.txt_dosis);
            txtJumlahHst = mView.findViewById(R.id.txt_jumlah_hst);
            txtSatuanHst = mView.findViewById(R.id.txt_satuan_hst);
        }
    }
}
