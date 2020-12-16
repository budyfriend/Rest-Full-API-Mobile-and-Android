package com.example.restfullapi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restfullapi.R;
import com.example.restfullapi.dialog.dialogData;
import com.example.restfullapi.model.modelMahasiswa;
import com.example.restfullapi.services.RequestData;

import java.util.ArrayList;

public class adapterMahasiswa extends RecyclerView.Adapter<adapterMahasiswa.MahasiswaViewHolder> {
    ArrayList<modelMahasiswa> modelMahasiswaArrayList;
    Context context;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RequestData requestData;

    public adapterMahasiswa(ArrayList<modelMahasiswa> modelMahasiswaArrayList, Context context,RecyclerView recyclerView,ProgressDialog progressDialog) {
        this.modelMahasiswaArrayList = modelMahasiswaArrayList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressDialog = progressDialog;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        return new MahasiswaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        holder.viewBind(modelMahasiswaArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelMahasiswaArrayList.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,
                tv_jurusan,
                tv_semester;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_jurusan = itemView.findViewById(R.id.tv_jurusan);
            tv_semester = itemView.findViewById(R.id.tv_semester);
        }

        @SuppressLint("SetTextI18n")
        public void viewBind(modelMahasiswa modelMahasiswa) {

            tv_nama.setText("Nama : " + modelMahasiswa.getNama());
            tv_jurusan.setText("Jurusan : " + modelMahasiswa.getJurusan());
            tv_semester.setText("Semester : " + modelMahasiswa.getSemester());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialogData dialogData = new dialogData(context,"update",modelMahasiswa,recyclerView);
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    dialogData.show(manager,"fm-dialog");
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestData = new RequestData(context,progressDialog,recyclerView);
                            requestData.deleteData(modelMahasiswa);
                        }
                    }).setMessage("Apa kamu yakin ingin menghapus data ini?")
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
        }
    }
}
