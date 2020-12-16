package com.example.restfullapi.services;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RequestData {

    public static void getData(Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {
        String link_api = context.getString(R.string.link_api);
        ArrayList<modelMahasiswa> modelMahasiswa = new ArrayList<>();

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(link_api, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    adapterMahasiswa adapterMahasiswa;
                    JSONArray jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String nama = object.getString("nama");
                        String jurusan = object.getString("jurusan");
                        String semester = object.getString("semester");

                        modelMahasiswa.add(new modelMahasiswa(id, nama, jurusan, semester));
                    }
                    adapterMahasiswa = new adapterMahasiswa(modelMahasiswa, context, recyclerView, progressDialog);
                    recyclerView.setAdapter(adapterMahasiswa);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void pushData(Context context, ProgressDialog progressDialog, modelMahasiswa mahasiswa, Dialog dialog, RecyclerView recyclerView) {
        String link_api = context.getString(R.string.link_api);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

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
                RequestData.getData(context, progressDialog, recyclerView);
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });
    }

    public static void putData(Context context, ProgressDialog progressDialog, modelMahasiswa mahasiswa, Dialog dialog, RecyclerView recyclerView) {
        String link_api = context.getString(R.string.link_api);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        params.put("id", Integer.parseInt(mahasiswa.getId()));
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
                RequestData.getData(context, progressDialog, recyclerView);
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });
    }

    public static void deleteData(Context context, ProgressDialog progressDialog, modelMahasiswa mahasiswa, RecyclerView recyclerView) {
        String link_api = context.getString(R.string.link_api);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        params.put("id", Integer.parseInt(mahasiswa.getId()));
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
                RequestData.getData(context, progressDialog, recyclerView);
                Toast.makeText(context, "response : " + responseString+ " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


    }

}
