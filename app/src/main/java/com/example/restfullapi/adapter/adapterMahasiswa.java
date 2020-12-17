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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restfullapi.R;
import com.example.restfullapi.dialog.dialogData;
import com.example.restfullapi.model.modelMahasiswa;
import com.example.restfullapi.services.RequestData;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class adapterMahasiswa extends RecyclerView.Adapter<adapterMahasiswa.MahasiswaViewHolder> {
    ArrayList<modelMahasiswa> modelMahasiswaArrayList;
    Context context;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RequestData requestData;

    public adapterMahasiswa(ArrayList<modelMahasiswa> modelMahasiswaArrayList, Context context, RecyclerView recyclerView, ProgressDialog progressDialog) {
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
        public void viewBind(modelMahasiswa mMahasiswa) {


            tv_nama.setText("Nama : " + mMahasiswa.getNama());
            tv_jurusan.setText("Jurusan : " + mMahasiswa.getJurusan());
            tv_semester.setText("Semester : " + mMahasiswa.getSemester());
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialogData dialogData = new dialogData(context, "update", mMahasiswa, recyclerView);
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    dialogData.show(manager, "fm-dialog");
                    return true;
                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestData = new RequestData(context, progressDialog, recyclerView);
//                            requestData.deleteData(mMahasiswa);
//                        }
//                    }).setMessage("Apa kamu yakin ingin menghapus data ini?")
//                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).show();
//                }
//            });


        }
    }

//    modelMahasiswa mhs = null;
//    int id;
//    String nama, jurusan, semester;
//
//    public ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            int position = viewHolder.getAdapterPosition();
//            switch (direction) {
//                case ItemTouchHelper.LEFT:
//
//                    id = modelMahasiswaArrayList.get(position).getId();
//                    nama = modelMahasiswaArrayList.get(position).getNama();
//                    jurusan = modelMahasiswaArrayList.get(position).getJurusan();
//                    semester = modelMahasiswaArrayList.get(position).getSemester();
//
//                    mhs = new modelMahasiswa(id, nama, jurusan, semester);
////                    requestData.deleteData(modelMahasiswaArrayList.get(position).getId());
//                    modelMahasiswaArrayList.remove(position);
//                    notifyItemRemoved(position);
//
//
//                    new AlertDialog.Builder(viewHolder.itemView.getContext())
//                            .setMessage("Are you sure?")
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    modelMahasiswaArrayList.add(position, mhs);
////                                    requestData.pushData(mhs, null);
//                                    notifyItemInserted(position);
////                                    notifyDataSetChanged();
//                                }
//                            })
//                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    modelMahasiswaArrayList.add(position, mhs);
//                                    requestData.deleteData(modelMahasiswaArrayList.get(position).getId());
//                                    modelMahasiswaArrayList.remove(position);
//                                    notifyItemRemoved(position);
//                                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
//                                    Snackbar.make(recyclerView, "Successful delete", Snackbar.LENGTH_LONG).show();
//
//                                }
//                            }).create()
//                            .show();
//
////                    Snackbar.make(recyclerView, "Successful delete", Snackbar.LENGTH_LONG)
////                            .setAction("Undo", new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////
////                                    modelMahasiswaArrayList.add(position, mhs);
//////                                    requestData.pushData(mhs, null);
////                                    notifyItemInserted(position);
//////                                    notifyDataSetChanged();
////                                }
////                            }).show();
//                    break;
//                case ItemTouchHelper.RIGHT:
//
//            }
//
////            new AlertDialog.Builder(viewHolder.itemView.getContext())
////                    .setMessage("Are you sure?")
////                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            notifyDataSetChanged();
////                        }
////                    })
////                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////
////                            requestData = new RequestData(context, progressDialog, recyclerView);
////                            requestData.deleteData(modelMahasiswaArrayList.get(position).getId());
////
////                        }
////                    }).create()
////                    .show();
//
//        }
//    };


}
