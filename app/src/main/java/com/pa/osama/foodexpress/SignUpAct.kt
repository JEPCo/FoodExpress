package com.pa.osama.foodexpress

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signup_signup_button.setOnClickListener {

            if (signup_phone_et.text.toString().length < 10)
                Toast.makeText(this, "Invalid Phone Num.", Toast.LENGTH_LONG).show()
            else {
                if (signup_password_et.text.toString() != signup_passwordconfirm_et.text.toString())
                    Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
                else
                {
                    var pd = ProgressDialog(this)
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    pd.setMessage("Please Wait ..")
                    pd.show()


                    var web = AppInfo.url + "signup.php"
                    var rq = Volley.newRequestQueue(this)
                    var sr = object:StringRequest(Request.Method.POST, web,
                            Response.Listener { response ->
                                pd.hide()
                                if (response == "0") {
                                    Toast.makeText(this, "Mobile Already Used", Toast.LENGTH_LONG).show()

                                } else
                                {
                                    AppInfo.mobile = signup_phone_et.text.toString()
                                    startActivity(Intent(this, MenuAct::class.java))
                                    finish()
                                }

                            },
                            Response.ErrorListener { error ->
                                pd.hide()
                                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                            })
                    {
                        override fun getParams(): MutableMap<String, String> {
                            var map = HashMap<String, String>()
                            map.put("mobile", signup_phone_et.text.toString())
                            map.put("password", signup_password_et.text.toString())
                            map.put("name", signup_name_et.text.toString())

                            return map
                        }
                    }
                    rq.add(sr)
                }
            }
        }
    }
}
