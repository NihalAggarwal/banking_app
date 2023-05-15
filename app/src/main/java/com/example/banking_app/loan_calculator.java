package com.example.banking_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class loan_calculator extends AppCompatActivity {

    Spinner spinnerLanguages;
    AutoCompleteTextView autoCompleteTextView1,autoCompleteTextView2,autoCompleteTextView3,autoCompleteTextView4,autoCompleteTextView5;
    TextInputLayout t1,t2,t3,t4,t5,t6;
    Button b1;
    ProgressBar pb;
//    RequestQueue queue;
    String loan_link = "https://android-apis.onrender.com/predict_loan";
    TextView loan;
    DatabaseReference db_loan;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);
//        queue = Volley.newRequestQueue(this);
        pb = findViewById(R.id.progress_loan);
        loan = findViewById(R.id.loan_status);
        t1 = findViewById(R.id.dependents);
        t2 = findViewById(R.id.applicant_income);
        t3 = findViewById(R.id.co_applicant_income);
        t4 = findViewById(R.id.loan_amount);
        t5 = findViewById(R.id.loan_amount_term);
        t6 = findViewById(R.id.property);
        b1 = findViewById(R.id.predict_button);

        autoCompleteTextView1 = findViewById(R.id.AutoCompleteTextview1);
        autoCompleteTextView2 = findViewById(R.id.AutoCompleteTextview2);
        autoCompleteTextView3 = findViewById(R.id.AutoCompleteTextview3);
        autoCompleteTextView4 = findViewById(R.id.AutoCompleteTextview4);
        autoCompleteTextView5 = findViewById(R.id.AutoCompleteTextview5);
        //We will use this data to inflate the drop-down items
        String[] Subjects1 = new String[]{"Male", "Female"};
        String[] Subjects2 = new String[]{"Married", "Unmarried"};
        String[] Subjects3 = new String[]{"Graduate", "Not Graduate"};
        String[] Subjects4 = new String[]{"Yes", "No"};
        String[] Subjects5 = new String[]{"Yes", "No"};

        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects5);
        autoCompleteTextView1.setAdapter(adapter1);
        autoCompleteTextView2.setAdapter(adapter2);
        autoCompleteTextView3.setAdapter(adapter3);
        autoCompleteTextView4.setAdapter(adapter4);
        autoCompleteTextView5.setAdapter(adapter5);

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView1.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView2.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView3.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView4.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTextView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView5.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        if(autoCompleteTextView1.getText().toString().isEmpty()){
//            loan.setText("True");
//        }
//        else{
//            loan.setText("False");
//        }

        b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                if(!t1.getEditText().getText().toString().isEmpty() && !t2.getEditText().getText().toString().isEmpty() &&
                        !t3.getEditText().getText().toString().isEmpty() && !t4.getEditText().getText().toString().isEmpty()
                        && !t5.getEditText().getText().toString().isEmpty() && !t6.getEditText().getText().toString().isEmpty() &&
                !autoCompleteTextView1.getText().toString().isEmpty() && !autoCompleteTextView2.getText().toString().isEmpty() &&
                        !autoCompleteTextView3.getText().toString().isEmpty() && !autoCompleteTextView4.getText().toString().isEmpty() &&
                        !autoCompleteTextView5.getText().toString().isEmpty()
                ) {

                    
                    b1.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
//                    start_loan_db(this);
                    loan_db_manager db = new loan_db_manager(loan_calculator.this);
                    String result = db.addRecord(autoCompleteTextView1.getText().toString(),autoCompleteTextView2.getText().toString(),t1.getEditText().getText().toString(),autoCompleteTextView3.getText().toString(),autoCompleteTextView5.getText().toString(),t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),t4.getEditText().getText().toString(),t5.getEditText().getText().toString(),autoCompleteTextView4.getText().toString(),t6.getEditText().getText().toString());
                    Toast.makeText(loan_calculator.this,result,Toast.LENGTH_LONG).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loan_link,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    db_loan = FirebaseDatabase.getInstance().getReference().child("Loan_Calculator");
                                    String gender = (autoCompleteTextView1.getText().toString());
                                    String married = (autoCompleteTextView2.getText().toString());
                                    String graduated = (autoCompleteTextView3.getText().toString());
                                    String credit_history = (autoCompleteTextView4.getText().toString());
                                    String employment_status = autoCompleteTextView5.getText().toString();
                                    String dependents = t1.getEditText().getText().toString();
                                    String applicant_income = t2.getEditText().getText().toString();
                                    String co_applicant_income = t3.getEditText().getText().toString();
                                    String loan_amount = t4.getEditText().getText().toString();
                                    String loan_term = t5.getEditText().getText().toString();
                                    String property = t6.getEditText().getText().toString();



                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("Loan");
                                        Random rnd = new Random();
                                        int n = 1000000000 + rnd.nextInt(900000000);
                                        String id = Integer.toString(n);
                                        loan_db_model loan_db_model = new loan_db_model(gender,married,graduated,credit_history,employment_status,dependents,applicant_income,co_applicant_income,loan_amount,loan_term,property,data);
                                        db_loan.child(id).setValue(loan_db_model);
                                        if (data.equals("Passed")) {
                                            Toast.makeText(loan_calculator.this, "You are eligible for Loan", Toast.LENGTH_LONG).show();
                                            loan.setText("You are eligible for Loan");
                                        } else {
                                            Toast.makeText(loan_calculator.this, "You are not eligible for Loan", Toast.LENGTH_LONG).show();
                                            loan.setText("You are not eligible for Loan");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    b1.setVisibility(View.VISIBLE);
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

//                                loan.setText(error.printStackTrace());
                                    Toast.makeText(loan_calculator.this, "API Overload", Toast.LENGTH_SHORT).show();
                                    b1.setVisibility(View.VISIBLE);
                                    pb.setVisibility(View.GONE);
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
                            if (autoCompleteTextView1.getText().toString().equals("Male")) {

                                params.put("Gender", "0");
                            } else if (autoCompleteTextView1.getText().toString().equals("Female")) {
                                params.put("Gender", "1");
                            }
                            if (autoCompleteTextView2.getText().toString().equals("Married")) {
//                            Toast.makeText(loan_calculator.this,"Helo female",Toast.LENGTH_LONG).show();
                                params.put("Married", "1");
                            } else if (autoCompleteTextView2.getText().toString().equals("Unmarried")) {
                                params.put("Married", "0");
                            }
                            if (autoCompleteTextView3.getText().toString().equals("Graduate")) {
                                params.put("Education", "1");
                            } else if (autoCompleteTextView3.getText().toString().equals("Not Graduate")) {
                                params.put("Education", "0");
                            }
                            if (autoCompleteTextView4.getText().toString().equals("Yes")) {
                                params.put("Credit_History", "1");
                            } else if (autoCompleteTextView4.getText().toString().equals("No")) {
                                params.put("Credit_History", "0");
                            }

                            if (autoCompleteTextView5.getText().toString().equals("Yes")) {
                                params.put("Self_Employed", "1");
                            } else if (autoCompleteTextView5.getText().toString().equals("No")) {
                                params.put("Self_Employed", "0");
                            }

                            params.put("Dependents", t1.getEditText().getText().toString());
                            params.put("ApplicantIncome", t2.getEditText().getText().toString());
                            params.put("CoapplicantIncome", t3.getEditText().getText().toString());
                            params.put("LoanAmount", t4.getEditText().getText().toString());
                            params.put("Loan_Amount_Term", t5.getEditText().getText().toString());
                            params.put("Property_Area", t6.getEditText().getText().toString());
//                        loan.setText(params.toString());

                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(loan_calculator.this);
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(loan_calculator.this, "Enter all the information above!", Toast.LENGTH_SHORT).show();
                    b1.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

    public void start_loan_db(View view){


    }
}