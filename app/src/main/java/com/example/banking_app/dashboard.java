package com.example.banking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.banking_app.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

public class dashboard extends AppCompatActivity implements PaymentResultListener, PaymentResultWithDataListener {
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new home()).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;

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

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,temp).commit();
                return true;
            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(dashboard.this,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(dashboard.this,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(dashboard.this,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(dashboard.this,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}