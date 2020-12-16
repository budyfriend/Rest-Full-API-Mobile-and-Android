package com.example.restfullapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.restfullapi.dialog.dialogData;
import com.example.restfullapi.services.RequestData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);
        context = this;
        progressDialog = new ProgressDialog(context);

        RequestData.getData(context, progressDialog, recyclerView);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogData dialogData = new dialogData(context, "add", recyclerView);
                dialogData.show(getSupportFragmentManager(), "fm-dialog");
            }
        });
    }

}