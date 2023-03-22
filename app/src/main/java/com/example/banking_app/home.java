package com.example.banking_app;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;


public class home extends Fragment implements PaymentResultListener, PaymentResultWithDataListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
//        Checkout.preload(getActivity());

        ImageView image = rootView.findViewById(R.id.image1);
//
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
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
            options.put("amount", "50000");//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","7984226960");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Toast.makeText(getActivity(),"Payment Success!",Toast.LENGTH_LONG).show();
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
            System.out.println(e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getActivity(),"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getActivity(),"Payment Failed!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(getActivity(),"Payment Success!",Toast.LENGTH_SHORT).show();
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(getActivity(),"Payment Failed!",Toast.LENGTH_LONG).show();
        System.out.println(s);
    }
}