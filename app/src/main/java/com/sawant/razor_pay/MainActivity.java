package com.sawant.razor_pay;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private Button clickButton;
    private EditText merchantNameEditText;
    private EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickButton = (Button) findViewById(R.id.clickButton);
        merchantNameEditText = (EditText) findViewById(R.id.merchantNameEditText);
        amountEditText = (EditText) findViewById(R.id.amountEditText);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantNameEditText!=null
                        && merchantNameEditText.getText().toString()!=null
                        && !TextUtils.isEmpty(merchantNameEditText.getText().toString())
                        && amountEditText!=null
                        && amountEditText.getText().toString()!=null
                        && !TextUtils.isEmpty(amountEditText.getText().toString())) {
                    startPayment(merchantNameEditText.getText().toString(),amountEditText.getText().toString());
                }
                else {
                    Snackbar.make(v, "Please Enter Correct Payment Details", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Checkout.preload(getApplicationContext());

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("", s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("", s);

    }


    public void startPayment(String merchantName, String paymentAmount) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.rzp_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", merchantName);

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */

            Random random = new Random( System.currentTimeMillis() );
            int orderNumber =  10000 + random.nextInt(20000);
            options.put("description", "Order #" + orderNumber);

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */

            options.put("amount", paymentAmount);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
