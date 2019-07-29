package com.pa.osama.foodexpress

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart.*

class CartAct : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        showCart()
        cart_confirm_btn.setOnClickListener {
            var m = getSystemService(LOCATION_SERVICE) as LocationManager
            var s = object: LocationListener
            {
                override fun onLocationChanged(p0: Location) {
                    var web = AppInfo.url + "add_bill.php"
                    var rq = Volley.newRequestQueue(applicationContext)
                    var sr = object : StringRequest(Request.Method.POST, web,
                            Response.Listener { response ->
                                
                            }
                            ,Response.ErrorListener {error ->
                        Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                    })

                    {
                        override fun getParams(): MutableMap<String, String> {
                            var hm = HashMap<String, String>()
                            hm.put("mobile", AppInfo.mobile)
                            hm.put("total", AppInfo.total.toString())
                            hm.put("lon", p0.longitude.toString())
                            hm.put("lat", p0.latitude.toString())
                            return hm
                        }
                    }

                    rq.add(sr)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

                }

                override fun onProviderEnabled(p0: String?) {

                }

                override fun onProviderDisabled(p0: String?) {

                }

            }
            m.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0f, s)

        }
    }

    fun showCart()
    {
        var web = AppInfo.url + "show_cart.php?mobile=" + AppInfo.mobile
        var pd = ProgressDialog(this)
        pd.setMessage("Please Wait ..")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()
        var rq= Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, web, null,
                Response.Listener { response ->
                    pd.hide()
                    var list= ArrayList<Cart>()
                    list.clear()
                    AppInfo.total = 0.0
                    for (x in 0..response.length()-1) {
                        list.add(Cart(response.getJSONObject(x).getInt("id"),
                                response.getJSONObject(x).getString("name"),
                                response.getJSONObject(x).getDouble("price"),
                                response.getJSONObject(x).getInt("qty")))
                        AppInfo.total += response.getJSONObject(x).getDouble("price") * response.getJSONObject(x).getInt("qty")
                    }
                    var rvAdapter = CartRVAdapter(this, list)
                    cartact_rv.adapter = rvAdapter
                    cartact_rv.layoutManager = GridLayoutManager(this,2)
                    cartact_total_tv.text = "Total is: " + AppInfo.total.toString()
                },
                Response.ErrorListener { error ->
                    pd.hide()
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
        rq.add(jar)
    }
}
