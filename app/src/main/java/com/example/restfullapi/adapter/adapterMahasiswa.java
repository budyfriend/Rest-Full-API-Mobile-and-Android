package com.example.restfullapi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restfullapi.R;
import com.example.restfullapi.activity.InputActivity;
import com.example.restfullapi.model.modelMahasiswa;
import com.example.restfullapi.services.RequestDataAsyncHttp;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.restfullapi.services.RequestDataAsyncHttp.deleteDataAsyncHttp;
import static com.example.restfullapi.services.RequestDataVolley.deleteDataVolley;

public class adapterMahasiswa extends RecyclerView.Adapter<adapterMahasiswa.MahasiswaViewHolder> {
    ArrayList<modelMahasiswa> modelMahasiswaArrayList;
    Context context;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RequestDataAsyncHttp requestDataAsyncHttp;

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
//                    dialogData dialogData = new dialogData(context, "update", mMahasiswa, recyclerView);
//                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
//                    dialogData.show(manager, "fm-dialog");

                    Intent intent = new Intent(context, InputActivity.class);
                    intent.putExtra("pilih","update");
                    intent.putExtra("id",mMahasiswa.getId());
                    intent.putExtra("nama",mMahasiswa.getNama());
                    intent.putExtra("jurusan",mMahasiswa.getJurusan());
                    intent.putExtra("semester",mMahasiswa.getSemester());

                    context.startActivity(intent);
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

    modelMahasiswa mhs = null;
    int id;
    String nama, jurusan, semester;

    public ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:

                    id = modelMahasiswaArrayList.get(position).getId();
                    nama = modelMahasiswaArrayList.get(position).getNama();
                    jurusan = modelMahasiswaArrayList.get(position).getJurusan();
                    semester = modelMahasiswaArrayList.get(position).getSemester();

                    mhs = new modelMahasiswa(id, nama, jurusan, semester);
//                    requestData.deleteData(modelMahasiswaArrayList.get(position).getId());
                    modelMahasiswaArrayList.remove(position);
                    notifyItemRemoved(position);

                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setCancelable(false)
                            .setMessage("Are you sure?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    modelMahasiswaArrayList.add(position, mhs);
//                                    requestData.pushData(mhs, null);
                                    notifyItemInserted(position);
//                                    notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    modelMahasiswaArrayList.add(position, mhs);
                                    deleteDataAsyncHttp(modelMahasiswaArrayList.get(position).getId(),context,progressDialog,recyclerView);
                                    modelMahasiswaArrayList.remove(position);
//                                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


                                }
                            }).create()
                            .show();

//                    Snackbar.make(recyclerView, "Successful delete", Snackbar.LENGTH_LONG)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    modelMahasiswaArrayList.add(position, mhs);
////                                    requestData.pushData(mhs, null);
//                                    notifyItemInserted(position);
////                                    notifyDataSetChanged();
//                                }
//                            }).show();
                    break;


            }

//            new AlertDialog.Builder(viewHolder.itemView.getContext())
//                    .setMessage("Are you sure?")
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            notifyDataSetChanged();
//                        }
//                    })
//                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            requestData = new RequestData(context, progressDialog, recyclerView);
//                            requestData.deleteData(modelMahasiswaArrayList.get(position).getId());
//
//                        }
//                    }).create()
//                    .show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(context,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context,R.color.purple_500))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create().decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}
