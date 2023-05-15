package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class insurance extends AppCompatActivity {
    TextView insurance_api;

    public void mutualFundList()

    {
        String url = "https://latest-mutual-fund-nav.p.rapidapi.com/fetchLatestNAV";

        RequestQueue requestQueue = Volley.newRequestQueue(insurance.this);

        String uri = Uri.parse(url)
                .buildUpon()
                .build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(insurance.this,"Data Received",Toast.LENGTH_LONG).show();
                Log.d("VolleyResponse", "response: " + response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                insurance_model[] data= gson.fromJson(response.replaceAll("\\s", ""),insurance_model[].class);
//                System.out.println(response.replaceAll("\\s",""));
                insurance_adapter adapter = new insurance_adapter(data);
//                adapter.

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insurance.this,"API Error",Toast.LENGTH_LONG).show();
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("X-RapidAPI-Host", "latest-mutual-fund-nav.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "6c525d2bc3msh14392b9b7f29002p1ccac4jsn6a1eb99b6f88");   //changed key
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(stringRequest);
    }

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        recyclerView = findViewById(R.id.insurance_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mutualFundList();
//        insurance_api = findViewById(R.id.insurance_api_data);
//
//        insurance_api.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mutualFundList();
//            }
//        });

    }


}