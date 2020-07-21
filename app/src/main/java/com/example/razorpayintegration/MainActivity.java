    package com.example.razorpayintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

    public class MainActivity extends AppCompatActivity implements PaymentResultListener, View.OnClickListener {

        private static final String TAG = MainActivity.class.getSimpleName();
        private Checkout checkout=new Checkout();
        private Button paynow;
        private EditText editText;




        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intID();

        Checkout.preload(getApplicationContext());


    }

            private void intID() {
                paynow=findViewById(R.id.bt_paynow);
                paynow.setOnClickListener(this);
                editText=findViewById(R.id.editText);
            }

        public void startPayment() {
            checkout.setKeyID("rzp_test_odXvTRTdeXdCuj");
            /**
             * Instantiate Checkout
             */

            /**
             * Set your logo here
             */
//            checkout.setImage(R.drawable.logo);

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
                 * eg: ACME Corp || HasGeek etc.
                 */
                options.put("name", "Merchant Name");

                /**
                 * Description can be anything
                 * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
                 *     Invoice Payment
                 *     etc.
                 */
                options.put("description", "Reference No. #123456");
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//                options.put("order_id", "order_9A33XWu170gUtm");
                options.put("currency", "INR");

                /**
                 * Amount is always passed in currency subunits
                 * Eg: "500" = INR 5.00
                 */

                String getAmount=editText.getText().toString();
                double total = Double.parseDouble(getAmount);
                total=total*100;
                options.put("amount", total);

                checkout.open(activity, options);
            } catch(Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }

        @Override
        public void onPaymentSuccess(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaymentError(int i, String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_paynow:
                    startPayment();
                    break;
            }
        }
    }
