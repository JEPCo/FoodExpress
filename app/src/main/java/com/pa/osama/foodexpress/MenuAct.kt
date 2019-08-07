package com.pa.osama.foodexpress

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_menu.*

class MenuAct : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        var web = AppInfo.url + "get_items.php?category=" + tab.text
        var pd = ProgressDialog(this)
        pd.setMessage("Please Wait ..")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()
        var rq= Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, web, null,
                Response.Listener { response ->
                    pd.hide()
                    var list= ArrayList<Meal>()
                    for (x in 0..response.length()-1)
                        list.add(Meal(response.getJSONObject(x).getInt("id"),
                                response.getJSONObject(x).getString("name"),
                                response.getJSONObject(x).getDouble("price"),
                                response.getJSONObject(x).getString("photo")))
                    var rvAdapter = MealRVAdapter(this, list)
                    menu_rv.adapter = rvAdapter
                    menu_rv.layoutManager = LinearLayoutManager(this)
                },
                Response.ErrorListener { error ->
                    pd.hide()
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
        rq.add(jar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menu_cat_tabs.addOnTabSelectedListener(this)

        var web = AppInfo.url + "get_cat.php"
        var pd = ProgressDialog(this)
        pd.setMessage("Please Wait ..")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()

        var rq= Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, web, null,
                Response.Listener { response ->
                    pd.hide()
                    for (x in 0..response.length()-1)
                        menu_cat_tabs.addTab(menu_cat_tabs.newTab().setText(response.getJSONObject(x).getString("category")))
                },
                Response.ErrorListener { error ->
                    pd.hide()
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
        rq.add(jar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_exit)
        {
            var sp = getSharedPreferences("mydata", Context.MODE_PRIVATE)
            var edi = sp.edit()
            edi.remove("mobile")
            edi.commit()
            AppInfo.mobile = ""
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        if (item.itemId == R.id.item_cart)
        {
            startActivity(Intent(this, CartAct::class.java))
        }

        if (item.itemId == R.id.item_chat)
        {
            startActivity(Intent(this, ChatAct::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
