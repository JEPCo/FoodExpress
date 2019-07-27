package com.pa.osama.foodexpress


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_qty.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class QtyFragment : android.support.v4.app.DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_qty, container, false)
        v.frag_qty_btn.setOnClickListener {

            var web = AppInfo.url + "add_cart.php"
            var rq = Volley.newRequestQueue(activity)
            var sr = object : StringRequest(Request.Method.POST, web,
                    Response.Listener { response ->
                        Toast.makeText(activity, "Saved to Cart!", Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                    ,Response.ErrorListener {error ->
                        Toast.makeText(activity, error.message, Toast.LENGTH_LONG).show()
            })

            {
                override fun getParams(): MutableMap<String, String> {
                    var hm = HashMap<String, String>()
                    hm.put("itemid", AppInfo.itemid.toString())
                    hm.put("qty", v.frag_qty_et.text.toString())
                    hm.put("mobile", AppInfo.mobile)
                    return hm
                }
            }

            rq.add(sr)
        }

        return  v
    }


}

