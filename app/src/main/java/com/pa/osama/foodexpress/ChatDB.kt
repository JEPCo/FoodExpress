package com.pa.osama.foodexpress

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChatDB(con: Context) : SQLiteOpenHelper(con, "chat.db", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table chat(user text, msg text)")
        p0?.execSQL("insert into chat values(?,?)", arrayOf(AppInfo.mobile, "Hi"))
        p0?.execSQL("insert into chat values('Omar', 'Hello')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}