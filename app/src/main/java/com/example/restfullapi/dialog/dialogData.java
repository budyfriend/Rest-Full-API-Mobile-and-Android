package com.example.restfullapi.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restfullapi.R;
import com.example.restfullapi.model.modelMahasiswa;
import com.example.restfullapi.services.RequestData;

import java.util.ArrayList;
import java.util.Arrays;

public class dialogData extends DialogFragment {
    EditText et_nama,
            et_jurusan;
    Button btn_simpan;
    TextView tv_title;
    Spinner sp_semester;
    ProgressDialog progressDialog;
    Dialog dialog;
    RequestData requestData;


    Context context;
    String pilih;
    modelMahasiswa mahasiswa;
    RecyclerView recyclerView;

    public dialogData(Context context, String pilih, RecyclerView recyclerView) {
        this.context = context;
        this.pilih = pilih;
        this.recyclerView = recyclerView;
    }

    public dialogData(Context context, String pilih, modelMahasiswa mahasiswa, RecyclerView recyclerView) {
        this.context = context;
        this.pilih = pilih;
        this.mahasiswa = mahasiswa;
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment, container, false);
        progressDialog = new ProgressDialog(context);
        dialog = getDialog();

        sp_semester = v.findViewById(R.id.sp_semester);
        et_nama = v.findViewById(R.id.et_nama);
        et_jurusan = v.findViewById(R.id.et_jurusan);
        btn_simpan = v.findViewById(R.id.btn_simpan);
        tv_title = v.findViewById(R.id.tv_title);

        String[] angka_semester = {"1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(angka_semester));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
        sp_semester.setAdapter(arrayAdapter);
        requestData = new RequestData(context,progressDialog,recyclerView);

        if (pilih.equalsIgnoreCase("update")) {
            tv_title.setText("Ubah Data");
            et_nama.setText(mahasiswa.getNama());
            et_jurusan.setText(mahasiswa.getJurusan());
            setSpinner(sp_semester,mahasiswa.getSemester());
        }

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _semester = sp_semester.getSelectedItem().toString();
                String _nama = et_nama.getText().toString();
                String _jurusan = et_jurusan.getText().toString();
                if (_nama.isEmpty()) {
                    et_nama.setError("Data tidak boleh kosong");
                    et_nama.requestFocus();
                } else if (_jurusan.isEmpty()) {
                    et_jurusan.setError("Data tidak boleh kosong");
                    et_jurusan.requestFocus();
                } else {
                    if (pilih.equalsIgnoreCase("add")) {
                        mahasiswa = new modelMahasiswa( _nama, _jurusan, _semester);
                        requestData.pushData(mahasiswa, dialog);
                    }else {
                        mahasiswa = new modelMahasiswa(mahasiswa.getId(), _nama, _jurusan, _semester);
                        requestData.putData(mahasiswa, dialog);
                    }
                }


            }
        });
        return v;
    }

    private void setSpinner(Spinner sp, String s) {
        for (int i = 0; i < sp.getAdapter().getCount(); i++){
            if (sp.getAdapter().getItem(i).toString().contains(s)){
                sp.setSelection(i);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
