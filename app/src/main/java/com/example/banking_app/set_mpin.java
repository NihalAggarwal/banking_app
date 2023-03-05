package com.example.banking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class set_mpin extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
    TextView t1;
    DatabaseReference db_pins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mpin);
        e1 = findViewById(R.id.input1);
        e2 = findViewById(R.id.input2);
        e3 = findViewById(R.id.input3);
        e4 = findViewById(R.id.input4);
        e5 = findViewById(R.id.input5);
        e6 = findViewById(R.id.input6);
        t1 = findViewById(R.id.rsign_in);
        b1=findViewById(R.id.set_mpin);

        db_pins = FirebaseDatabase.getInstance().getReference().child("PINS");

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(set_mpin.this,login.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println(getIntent().getStringExtra("mobile"));
//                System.out.println(e1.getText().toString().trim() + e2.getText().toString().trim() + e3.getText().toString().trim()+ e4.getText().toString().trim()+ e5.getText().toString().trim()+ e6.getText().toString().trim());

                if(!e1.getText().toString().trim().isEmpty() && !e3.getText().toString().trim().isEmpty() && !e4.getText().toString().trim().isEmpty() && !e5.getText().toString().trim().isEmpty() && !e6.getText().toString().trim().isEmpty() && !e2.getText().toString().trim().isEmpty()){
                    insertData();
                    Toast.makeText(set_mpin.this,"M-PIN Updated Successfully!!!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(set_mpin.this,login.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(set_mpin.this,"Please enter all the digits!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        numbermove();
    }

    private void insertData() {
        String phone_num = getIntent().getStringExtra("mobile");
        System.out.println(phone_num);
        String pin = e1.getText().toString().trim() + e2.getText().toString().trim() + e3.getText().toString().trim()+ e4.getText().toString().trim()+ e5.getText().toString().trim()+ e6.getText().toString().trim();

        PINS pins = new PINS(pin);

        db_pins.child(phone_num).setValue(pins);

    }

    private void numbermove(){

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    e2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    e3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    e4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    e5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    e6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().isEmpty()){
                    e5.requestFocus();
                }
            }
        });
        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                b1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}