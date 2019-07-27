package com.pa.osama.foodexpress

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meal_layout.view.*

class MealRVAdapter(var con:Context, var mealList: ArrayList<Meal>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(con).inflate(R.layout.meal_layout, parent, false)
        return MyMeal(v)
    }

    override fun getItemCount(): Int {
        return  mealList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MyMeal).show(mealList[position].name, mealList[position].price, mealList[position].photo)
        (holder as MyMeal).itemView.meal_add_iv.setOnClickListener {
            AppInfo.itemid = mealList[position].id
            var obj = QtyFragment()
            obj.show((con as MenuAct).supportFragmentManager, "Qty")
        }
    }

    class MyMeal(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        fun show(nm: String, pr: Double, ph: String)
        {
            itemView.cart_name_tv.text = nm
            itemView.cart_price_tv.text = pr.toString()
            var pho = ph.replace(" ", "%20")
            Picasso.get().load(AppInfo.url + "images/" + pho).into(itemView.meal_photo_iv)
        }
    }
}