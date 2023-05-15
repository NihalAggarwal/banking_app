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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        DatabaseReference db;
        amountChange();

        db = FirebaseDatabase.getInstance().getReference().child("Amounts");

//        Checkout.preload(getActivity());
        t1 = rootView.findViewById(R.id.amount);
        balance = rootView.findViewById(R.id.balance);
        ImageView image = rootView.findViewById(R.id.image1);
        LinearLayout loan = rootView.findViewById(R.id.linlay4);
        LinearLayout insurance = rootView.findViewById(R.id.insurance);
        LinearLayout mutual_funds = rootView.findViewById(R.id.mutual_funds);

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new loan());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new home());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                openAmountDialogBox();
            }
        });

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()) {
                            String key = ds.getKey();
                            String currentAmount;
                            currentAmount = t1.getText().toString();
                            currentAmount = currentAmount.substring(2, currentAmount.length());
                            String entered_amount = ds.getValue().toString();
                            entered_amount = entered_amount.replace(",", "");
                            currentAmount = currentAmount.replace(",", "");
                            float current = Float.parseFloat(currentAmount);
                            float amount = Float.parseFloat(entered_amount);

                            if (current >= amount && key.equals("amount")) {
                                DecimalFormat df = new DecimalFormat();
                                df.setMaximumFractionDigits(2);
                                float diff = current - amount;
                                String ans = "Rs " + df.format(diff);
                                t1.setText(ans);
                                Toast.makeText(getActivity(), "Transaction Completed", Toast.LENGTH_LONG).show();
                                db.child("curent_amount").setValue(Float.toString(diff));
                            } else if (key.equals("curent_amount")) {
                                System.out.println();
                            } else {
                                Toast.makeText(getActivity(), "Insufficient Funds", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "Transaction Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                db.child("amount").setValue("0");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new home());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


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

    public void startPayment(String amount) {
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
            options.put("amount", amount);//pass amount in currency subunits
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
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getContext(),"Payment Success!",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(activity,"Payment Failed!",Toast.LENGTH_LONG).show();
        System.out.println(s);
    }

    public void openAmountDialogBox(){
        Amount_Dialog_Box amount_dialog_box = new Amount_Dialog_Box();
        amount_dialog_box.show(getActivity().getSupportFragmentManager(),"Amount");
    }

    private void amountChange(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Amounts");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String key = ds.getKey();
                    String value = ds.getValue().toString();

                    if(key.equals("curent_amount") && !value.equals(t1.getText().toString())){
                        t1.setText("Rs "+ value);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}