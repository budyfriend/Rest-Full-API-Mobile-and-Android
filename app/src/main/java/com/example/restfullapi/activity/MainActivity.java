package com.example.restfullapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.restfullapi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.restfullapi.services.RequestDataAsyncHttp.getDataAsyncHttp;
import static com.example.restfullapi.services.RequestDataVolley.getDataVolley;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        FloatingActionButton fab_add;
        Context context;
        ProgressDialog progressDialog;

        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);

        context = this;
        progressDialog = new ProgressDialog(context);
        getDataAsyncHttp(context,progressDialog,recyclerView);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogData dialogData = new dialogData(context, "add", recyclerView);
//                dialogData.show(getSupportFragmentManager(), "fm-dialog");

                Intent intent = new Intent(context,InputActivity.class);
                intent.putExtra("pilih","add");
                startActivity(intent);
            }
        });
    }



}