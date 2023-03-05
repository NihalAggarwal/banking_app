package com.example.banking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class register extends AppCompatActivity {

    TextView t1;
    Button b1;
    EditText e1;
    ProgressBar p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1 = findViewById(R.id.phone_no);
        t1 = findViewById(R.id.rsign_in);
        b1 = findViewById(R.id.get_otp);
        p1 = findViewById(R.id.progress_bar_get_otp);


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(register.this,login.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!e1.getText().toString().trim().isEmpty()){

                    if(e1.getText().toString().trim().length()==10){

                        p1.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + e1.getText().toString(), 60, TimeUnit.SECONDS, register.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                p1.setVisibility(View.GONE);
                                b1.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                p1.setVisibility(View.GONE);
                                b1.setVisibility(View.VISIBLE);
                                System.out.println(e1.getText().toString());
                                Toast.makeText(register.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backendotp, forceResendingToken);
                                p1.setVisibility(View.GONE);
                                b1.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(register.this,get_otp.class);
                                Intent intent2 = new Intent(register.this,set_mpin.class);
                                intent2.putExtra("mobile",e1.getText().toString());
                                intent.putExtra("mobile",e1.getText().toString());
                                intent.putExtra("backend_otp",backendotp);
                                startActivity(intent);
                            }
                        });

                    }
                    else{
                        Toast.makeText(register.this,"Please enter 10 digit number!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(register.this,"Please enter mobile number first!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}