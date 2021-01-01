package com.example.restfullapi.services;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.restfullapi.R;
import com.example.restfullapi.activity.MainActivity;
import com.example.restfullapi.adapter.adapterMahasiswa;
import com.example.restfullapi.model.modelMahasiswa;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestDataVolley {
    public static ProgressDialog progressDialog;
    public static RecyclerView recyclerView;
    public static String link_api;
    public static ArrayList<com.example.restfullapi.model.modelMahasiswa> modelMahasiswa = new ArrayList<>();
    public static ItemTouchHelper itemTouchHelper;

    private static void setLoading(ProgressDialog progressDialog) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public static void getDataVolley(Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {
        setLoading(progressDialog);
        link_api = context.getString(R.string.link_api);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    adapterMahasiswa adapterMahasiswa;
                    recyclerView.setAdapter(null);
                    JSONArray jsonArray = new JSONArray(response);
                    modelMahasiswa.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        String nama = object.getString("nama");
                        String jurusan = object.getString("jurusan");
                        String semester = object.getString("semester");

                        modelMahasiswa.add(new modelMahasiswa(id, nama, jurusan, semester));
                    }
                    adapterMahasiswa = new adapterMahasiswa(modelMahasiswa, context, recyclerView, progressDialog);

                    recyclerView.setAdapter(adapterMahasiswa);
                    itemTouchHelper = new ItemTouchHelper(adapterMahasiswa.simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void pushDataVolley(modelMahasiswa mahasiswa, Dialog dialog, Context context, ProgressDialog progressDialog) {
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Save successfully", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                context.startActivity(new Intent(context, MainActivity.class));
                if (dialog != null) {
//                    getData(context,progressDialog,recyclerView);
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(mahasiswa.getId()));
                params.put("nama", mahasiswa.getNama());
                params.put("jurusan", mahasiswa.getJurusan());
                params.put("semester", mahasiswa.getSemester());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void putDataVolley(modelMahasiswa mahasiswa, Dialog dialog, Context context, ProgressDialog progressDialog) {
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, link_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                context.startActivity(new Intent(context, MainActivity.class));
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(mahasiswa.getId()));
                params.put("nama", mahasiswa.getNama());
                params.put("jurusan", mahasiswa.getJurusan());
                params.put("semester", mahasiswa.getSemester());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void deleteDataVolley(int s, Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, link_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(recyclerView, "Successfully deleted", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            protected String getParamsEncoding() {
                return super.getParamsEncoding();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(s));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}
