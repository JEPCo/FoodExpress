package com.pa.osama.foodexpress

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >22)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), 123)

        login_new_tv.setOnClickListener {
            startActivity(Intent(this, SignUpAct::class.java))
        }

        var sp = getSharedPreferences("mydata", Context.MODE_PRIVATE)
        if (sp.getString("mobile", "") != "")
        {
            AppInfo.mobile = sp.getString("mobile", "")
            startActivity(Intent(this, MenuAct::class.java))
            finish()
        }

        login_login_button.setOnClickListener {

            var web: String = AppInfo.url + "login.php"
            var pd = ProgressDialog(this)
            pd.setMessage("Please Wait ..")
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.show()

            var rq = Volley.newRequestQueue(this)
            var sr = object: StringRequest(Request.Method.POST, web,
                    Response.Listener{response ->
                        pd.hide()
                        if (response == "1")
                        {
                            if (login_rem_cb.isChecked) {
                                var sp = getSharedPreferences("mydata", Context.MODE_PRIVATE)
                                var edi = sp.edit()
                                edi.putString("mobile", login_phone_et.text.toString())
                                edi.commit()
                            }
                            AppInfo.mobile = login_phone_et.text.toString()
                            startActivity(Intent(this, MenuAct::class.java))
                            finish()
                        } else
                        {
                            pd.hide()
                            Toast.makeText(this, "Login Failed!", Toast.LENGTH_LONG).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        pd.hide()
                        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    })

            {
                override fun getParams(): MutableMap<String, String> {
                    var map = HashMap<String, String>()
                    map.put("mobile", login_phone_et.text.toString())
                    map.put("password", login_password_et.text.toString())
                    return map
                }
            }
            rq.add(sr)



        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "شكراً لثقتك بمطعمنا", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this,"لا نستطيع الحصول على موقعك لذلك يرجى تحديد موقعك يدوياً بعد هملية الشراء", Toast.LENGTH_LONG).show()
        }
    }
}
