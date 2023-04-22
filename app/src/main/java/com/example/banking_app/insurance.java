package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

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
                Toast.makeText(insurance.this,"aa gaya mal",Toast.LENGTH_LONG).show();
                Log.d("VolleyResponse", "response: " + response);
                try {
                    JSONObject json= new JSONObject(response);

//                    insurance_api = findViewById(R.id.insurance_api_data)
//                    insurance_api.setText(json.toString());

                } catch (JSONException e) {
                    insurance_api = findViewById(R.id.insurance_api_data);
                    insurance_api.setText(e.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insurance.this,"Error aa gaya",Toast.LENGTH_LONG).show();
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
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        insurance_api = findViewById(R.id.insurance_api_data);

        insurance_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mutualFundList();
            }
        });

    }


}