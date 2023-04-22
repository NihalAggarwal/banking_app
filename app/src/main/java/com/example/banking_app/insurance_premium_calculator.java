package com.example.banking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class insurance_premium_calculator extends AppCompatActivity {
    AutoCompleteTextView ac1,ac2,ac3;
    TextInputLayout t1,t2,t3;
    Button button;
    ProgressBar progbar;
    TextView premium;
    String loan_link = "https://android-apis.onrender.com/calculate/premium";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_premium_calculator);

        t1 = findViewById(R.id.age);
        ac1 = findViewById(R.id.gender);
        t2 = findViewById(R.id.bmi);
        t3 = findViewById(R.id.children);
        ac2 = findViewById(R.id.habits);
        ac3 = findViewById(R.id.area);
        button = findViewById(R.id.calculate_premium);
        progbar = findViewById(R.id.progress_premium);
        premium = findViewById(R.id.premium_amount);

        String[] Subjects1 = new String[]{"Male", "Female"};
        String[] Subjects2 = new String[]{"Yes", "No"};
        String[] Subjects3 = new String[]{"North India","South India","West India","East India"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects3);

        ac1.setAdapter(adapter1);
        ac2.setAdapter(adapter2);
        ac3.setAdapter(adapter3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!t1.getEditText().getText().toString().isEmpty() && !t2.getEditText().getText().toString().isEmpty() &&
                        !t3.getEditText().getText().toString().isEmpty() &&
                        !ac1.getText().toString().isEmpty() && !ac2.getText().toString().isEmpty() &&
                        !ac3.getText().toString().isEmpty()
                ) {


                    button.setVisibility(View.GONE);
                    progbar.setVisibility(View.VISIBLE);


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loan_link,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Thread.sleep(10000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("Premium");
                                        double a = Double.parseDouble(data);
                                        int b = (int)a

                                                ;
                                        String ans = " Rs. ";
                                        ans+=Integer.toString(b);

                                        premium.setText(ans);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    button.setVisibility(View.VISIBLE);
                                    progbar.setVisibility(View.INVISIBLE);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

//                                loan.setText(error.printStackTrace());
                                    Toast.makeText(insurance_premium_calculator.this, "API Overload", Toast.LENGTH_SHORT).show();
                                    button.setVisibility(View.VISIBLE);
                                    progbar.setVisibility(View.GONE);
//                                loan_calculator.this.runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        Toast.makeText(loan_calculator.this, error.toString(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
////                                Toast.makeText(loan_calculator.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @NonNull
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            if (ac1.getText().toString().equals("Male")) {

                                params.put("sex", "0");
                            } else if (ac1.getText().toString().equals("Female")) {
                                params.put("sex", "1");
                            }
                            if (ac2.getText().toString().equals("Yes")) {
//                            Toast.makeText(loan_calculator.this,"Helo female",Toast.LENGTH_LONG).show();
                                params.put("smoker", "1");
                            } else if (ac2.getText().toString().equals("No")) {
                                params.put("smoker", "0");
                            }
                            if (ac3.getText().toString().equals("North India")) {
                                params.put("region", "1");
                            } else if (ac3.getText().toString().equals("South India")) {
                                params.put("region", "2");
                            } else if (ac3.getText().toString().equals("West India")) {
                                params.put("region", "3");
                            }
                            else if (ac3.getText().toString().equals("East India")) {
                                params.put("region", "4");
                            }

                            params.put("age", t1.getEditText().getText().toString());
                            params.put("bmi", t2.getEditText().getText().toString());
                            params.put("children", t3.getEditText().getText().toString());

//                        loan.setText(params.toString());

                            return params;
                        }

                    };

                    RequestQueue queue = Volley.newRequestQueue(insurance_premium_calculator.this);
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(insurance_premium_calculator.this, "Enter all the information above!", Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.VISIBLE);
                    progbar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}