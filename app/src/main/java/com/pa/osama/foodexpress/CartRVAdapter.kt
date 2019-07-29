package com.pa.osama.foodexpress

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cart_layout.view.*

class CartRVAdapter (var con: Context, var cartList: ArrayList<Cart>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(con).inflate(R.layout.cart_layout, parent, false)
        return MyCart(v)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MyCart).show(cartList[position].name, cartList[position].price, cartList[position].qty)
        (holder as MyCart).itemView.cart_delete_iv.setOnClickListener {
            var web = AppInfo.url + "del_cart.php?id=" + cartList[position].id
            var rq= Volley.newRequestQueue(con)
            var jar = StringRequest(Request.Method.GET, web,
                    Response.Listener { response ->
                        (con as CartAct).showCart()
                        Toast.makeText(con,"Deleted!", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(con, error.message, Toast.LENGTH_LONG).show()

                    })
            rq.add(jar)
        }
    }

    class MyCart(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun show(nm: String, pr: Double, qty: Int) {
            itemView.cart_name_tv.text = nm
            itemView.cart_price_tv.text = pr.toString()
            itemView.cart_qty_tv.text = qty.toString()
            var st = pr * qty
            itemView.cart_total_tv.text = st.toString()
        }
    }
}