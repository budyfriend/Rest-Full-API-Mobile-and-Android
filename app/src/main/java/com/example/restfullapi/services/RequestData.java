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
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    String link_api;
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();
    RequestData requestData;
    adapterMahasiswa adapterMahasiswa;
    ArrayList<modelMahasiswa> modelMahasiswa = new ArrayList<>();
    ItemTouchHelper itemTouchHelper;

    public RequestData(Context context, ProgressDialog progressDialog, RecyclerView recyclerView) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.recyclerView = recyclerView;
    }

    public void setLoading() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }


    public void getData() {
        setLoading();
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
                    itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pushData(modelMahasiswa mahasiswa, Dialog dialog) {
        requestData = new RequestData(context, progressDialog, recyclerView);
        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading();

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
                    requestData.getData();
                    dialog.dismiss();
                }

            }
        });
    }

    public void putData(modelMahasiswa mahasiswa, Dialog dialog) {
        requestData = new RequestData(context, progressDialog, recyclerView);
        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading();

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
                requestData.getData();
                Toast.makeText(context, "response : " + responseString + " status code : " + statusCode, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });
    }

    public void deleteData(int s) {
        requestData = new RequestData(context, progressDialog, recyclerView);
        client = new AsyncHttpClient();
        params = new RequestParams();
        link_api = context.getString(R.string.link_api);
        setLoading();
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


    modelMahasiswa mhs = null;
    int id;
    String nama, jurusan, semester;

    public ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            requestData = new RequestData(context, progressDialog, recyclerView);
            switch (direction) {
                case ItemTouchHelper.LEFT:

                    id = modelMahasiswa.get(position).getId();
                    nama = modelMahasiswa.get(position).getNama();
                    jurusan = modelMahasiswa.get(position).getJurusan();
                    semester = modelMahasiswa.get(position).getSemester();

                    mhs = new modelMahasiswa(id, nama, jurusan, semester);
//                    requestData.deleteData(modelMahasiswa.get(position).getId());
                    modelMahasiswa.remove(position);
                    adapterMahasiswa.notifyItemRemoved(position);
                    adapterMahasiswa.notifyDataSetChanged();

                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setMessage("Are you sure?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    modelMahasiswa.add(position, mhs);
//                                    requestData.pushData(mhs, null);
                                    adapterMahasiswa.notifyItemInserted(position);
//                                    notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    modelMahasiswa.add(position, mhs);
                                    requestData.deleteData(modelMahasiswa.get(position).getId());
                                    modelMahasiswa.remove(position);
                                    adapterMahasiswa.notifyItemRemoved(position);
                                    Snackbar.make(recyclerView, "Successful delete", Snackbar.LENGTH_LONG).show();

                                }
                            }).create()
                            .show();

//                    Snackbar.make(recyclerView, "Successful delete", Snackbar.LENGTH_LONG)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    modelMahasiswa.add(position, mhs);
////                                    requestData.pushData(mhs, null);
//                                    notifyItemInserted(position);
////                                    notifyDataSetChanged();
//                                }
//                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:

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
//                            requestData.deleteData(modelMahasiswa.get(position).getId());
//
//                        }
//                    }).create()
//                    .show();

        }
    };

}
