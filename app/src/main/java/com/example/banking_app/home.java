package com.example.banking_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.DecimalFormat;


public class home extends Fragment implements PaymentResultListener, PaymentResultWithDataListener{

    TextView t1,balance;
    LinearLayout l1,l2,l3,l4;
    DatabaseReference db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        t1 = rootView.findViewById(R.id.amount);
        amountChange();
        balance = rootView.findViewById(R.id.balance);
        ImageView image = rootView.findViewById(R.id.image1);
        ImageView image2 = rootView.findViewById(R.id.image2);
        ImageView image3 = rootView.findViewById(R.id.image3);
        ImageView image4 = rootView.findViewById(R.id.image4);

        LinearLayout insurance = rootView.findViewById(R.id.insurance);
        LinearLayout mutual_funds = rootView.findViewById(R.id.mutual_funds);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();

            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        l1 = rootView.findViewById(R.id.linlay1);
        l2 = rootView.findViewById(R.id.linlay2);
        l3 = rootView.findViewById(R.id.linlay3);
        l4 = rootView.findViewById(R.id.linlay4);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), insurance_premium_calculator.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        mutual_funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), insurance.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return rootView;
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_bkHp2bNRjoGk9T");

        checkout.setImage(R.drawable.splash);

        final Activity activity = getActivity();

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Banking App");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "100");//pass amount in currency subunits
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch(Exception e) {
//            Toast.makeText(Amount_Dialog_Box.this,"Payment Success!",Toast.LENGTH_LONG).show();
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
            System.out.println(e);
        }
    }
    Activity activity = getActivity();

    public void amountChange(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Amounts");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String key = ds.getKey();
                    String value = ds.getValue().toString();

                    if(key.equals("curent_amount")){
                        t1.setText("Rs "+ value);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(activity,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(activity,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(activity,"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(activity,"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }
}