package com.pa.osama.foodexpress

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*

class ChatAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var dbr = FirebaseDatabase.getInstance().reference
        var list = ArrayList<ChatModel>()
        var obj = ChatDB(this)
        var db = obj.writableDatabase

        var cur = db.rawQuery("select * from chat", null)
        cur.moveToFirst()

        while (!cur.isAfterLast)
        {
            list.add(ChatModel(cur.getString(0), cur.getString(1)))
            cur.moveToNext()
        }

        var adp = MSGRVAdapter(this, list)
        rv.adapter = adp
        rv.layoutManager = LinearLayoutManager(this)
        rv.scrollToPosition(list.size - 1)

        btn_chat.setOnClickListener {
            db.execSQL("insert into chat values(?,?)", arrayOf(AppInfo.mobile, et_chat.text.toString()))
            list.add((ChatModel(AppInfo.mobile, et_chat.text.toString())))

            dbr.child("users").child("b").setValue(et_chat.text.toString())
            adp.notifyDataSetChanged()
            rv.scrollToPosition(list.size - 1)
            et_chat.setText("")
        }

        dbr.child("users").child("a")
                .addValueEventListener(object : ValueEventListener
                {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        db.execSQL("insert into chat values(?,?)", arrayOf("b", p0?.value.toString()))
                        list.add(ChatModel("b", p0?.value.toString()))
                        adp.notifyDataSetChanged()
                        rv.scrollToPosition(list.size - 1)
                    }

                })
    }
}
