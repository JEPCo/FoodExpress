package com.pa.osama.foodexpress

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_paypal.*
import java.math.BigDecimal

class PaypalAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paypal)
            paypal_btn.setOnClickListener {

                var config= PayPalConfiguration()
                        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(AppInfo.cid)

                var i= Intent(this, PayPalService::class.java)
                i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
                startService(i)

                var payment= PayPalPayment(BigDecimal.valueOf(AppInfo.total),
                        "USD","Food Express App",
                        PayPalPayment.PAYMENT_INTENT_SALE)

                var obj=Intent(this,PaymentActivity::class.java)
                obj.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
                obj.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
                startActivityForResult(obj,123)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Thank you",
                        Toast.LENGTH_LONG).show()
            }
        }
    }
}
