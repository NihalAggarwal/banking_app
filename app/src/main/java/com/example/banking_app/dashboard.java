package com.example.banking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.banking_app.databinding.ActivityMainBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

public class dashboard extends AppCompatActivity implements PaymentResultListener, PaymentResultWithDataListener {
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    BottomAppBar bp;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new home(),"home_tag").commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bp = findViewById(R.id.bottomAppBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                String tag = "_tag";
                switch (item.getItemId()){
                    case R.id.home: temp = new home();
                    break;
                    case R.id.account: temp = new account();
                    break;
                    case R.id.loan: temp = new loan();
                    break;
                    case R.id.pay: temp = new pay();
                    break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,temp,temp.toString()+tag).commit();
                return true;
            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        db = FirebaseDatabase.getInstance().getReference().child("Amounts");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String key = ds.getKey();
                    String value = ds.getValue().toString();

                    if(key.equals("curent_amount")){
                        int a = Integer.parseInt(value);
                        a--;
                        db.child("curent_amount").setValue(Integer.toString(a));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(this,payment_success.class);
        startActivity(intent);
        Toast.makeText(this,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        db = FirebaseDatabase.getInstance().getReference().child("Amounts");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String key = ds.getKey();
                    String value = ds.getValue().toString();

                    if(key.equals("curent_amount")){
                        int a = Integer.parseInt(value);
                        a--;
                        db.child("curent_amount").setValue(Integer.toString(a));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(this,payment_success.class);
        startActivity(intent);
        Toast.makeText(this,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }
}