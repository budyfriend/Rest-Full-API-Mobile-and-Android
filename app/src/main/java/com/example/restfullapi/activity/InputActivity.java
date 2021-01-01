package com.example.restfullapi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restfullapi.R;
import com.example.restfullapi.model.modelMahasiswa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


import static com.example.restfullapi.services.RequestDataAsyncHttp.pushDataAsyncHttp;

import static com.example.restfullapi.services.RequestDataAsyncHttp.putDataAsyncHttp;
import static com.example.restfullapi.services.RequestDataVolley.pushDataVolley;
import static com.example.restfullapi.services.RequestDataVolley.putDataVolley;

public class InputActivity extends AppCompatActivity {
    EditText et_nama;
    Button btn_simpan;
    TextView tv_title;
    Spinner sp_semester,sp_jurusan;
    ProgressDialog progressDialog;
    int id;
    String nama,
            jurusan,
            semester;


    Context context;
    String pilih;
    modelMahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        context = this;

        progressDialog = new ProgressDialog(context);

        sp_semester = findViewById(R.id.sp_semester);
        et_nama = findViewById(R.id.et_nama);
        sp_jurusan = findViewById(R.id.sp_jurusan);
        btn_simpan = findViewById(R.id.btn_simpan);
        tv_title = findViewById(R.id.tv_title);


        String[] angka_semester = {"1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(angka_semester));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, arrayList);
        sp_semester.setAdapter(arrayAdapter);

        String[] pilihan_jurusan = {"Teknik Informatika",
                "Sistem Informasi",
                "Management Informasi"};
        ArrayList<String> arrayListJurusan = new ArrayList<String>(Arrays.asList(pilihan_jurusan));
        ArrayAdapter<String> arrayAdapterJurusan = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, arrayListJurusan);
        sp_jurusan.setAdapter(arrayAdapterJurusan);

        Bundle build = getIntent().getExtras();
        if (build != null) {
            pilih = build.getString("pilih");

            if (pilih.equalsIgnoreCase("update")) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Ubah Data");
                id = build.getInt("id");
                nama = build.getString("nama");
                jurusan = build.getString("jurusan");
                semester = build.getString("semester");

                tv_title.setText("Ubah Data");
                et_nama.setText(nama);
                setSpinner(sp_jurusan, jurusan);
                setSpinner(sp_semester, semester);
            }

        }


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _semester = sp_semester.getSelectedItem().toString();
                String _nama = et_nama.getText().toString();
                String _jurusan = sp_jurusan.getSelectedItem().toString();
                if (_nama.isEmpty()) {
                    et_nama.setError("Data tidak boleh kosong");
                    et_nama.requestFocus();
                }else {
                    if (pilih.equalsIgnoreCase("add")) {
                        mahasiswa = new modelMahasiswa(_nama, _jurusan, _semester);
                        pushDataAsyncHttp(mahasiswa, null, context, progressDialog);

                    } else {
                        mahasiswa = new modelMahasiswa(id, _nama, _jurusan, _semester);
                        putDataAsyncHttp(mahasiswa, null, context, progressDialog);
                    }
                }


            }
        });
    }

    private void setSpinner(Spinner sp, String s) {
        for (int i = 0; i < sp.getAdapter().getCount(); i++) {
            if (sp.getAdapter().getItem(i).toString().contains(s)) {
                sp.setSelection(i);
            }
        }
    }

}