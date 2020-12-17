package com.example.restfullapi.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.restfullapi.adapter.adapterMahasiswa;
import com.example.restfullapi.model.modelMahasiswa;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RequestData {
    Context context;
   public static  ProgressDialog progressDialog;
    public static RecyclerView recyclerView;
    public static String link_api;
    public static AsyncHttpClient client = new AsyncHttpClient();
    public static RequestParams params = new RequestParams();
    public static RequestData requestData;
    public static adapterMahasiswa adapterMahasiswa;
    public static ArrayList<modelMahasiswa> modelMahasiswa = new ArrayList<>();
    public static ItemTouchHelper itemTouchHelper;


    private static void setLoading(ProgressDialog progressDialog) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }


    public static void getData(Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {
        setLoading(progressDialog);
        client = new AsyncHttpClient();
        link_api = context.getString(R.string.link_api);
        client.get(link_api, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONArray jsonArray = new JSONArray(responseString);
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
        });
    }

    public static void pushData(modelMahasiswa mahasiswa, Dialog dialog, Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {

        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);

        params.put("id", mahasiswa.getId());
        params.put("nama", mahasiswa.getNama());
        params.put("jurusan", mahasiswa.getJurusan());
        params.put("semester", mahasiswa.getSemester());
        client.post(link_api, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                if (dialog != null) {
                    getData(context,progressDialog,recyclerView);
                    dialog.dismiss();
                }

            }
        });
    }

    public static void putData(modelMahasiswa mahasiswa, Dialog dialog, Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {

        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);

        params.put("id", mahasiswa.getId());
        params.put("nama", mahasiswa.getNama());
        params.put("jurusan", mahasiswa.getJurusan());
        params.put("semester", mahasiswa.getSemester());
        client.put(link_api, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                getData(context,progressDialog,recyclerView);
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });
    }

    public static void deleteData(int s,Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {

        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading(progressDialog);
        params.put("id", s);
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.delete(context, (link_api), entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                requestData.getData();
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


    }


}
