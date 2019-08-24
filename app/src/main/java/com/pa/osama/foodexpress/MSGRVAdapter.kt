package com.pa.osama.foodexpress

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class MSGRVAdapter(var con: Context, var list: ArrayList<ChatModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1)
            return SentHolder(LayoutInflater.from(con).inflate(R.layout.sent_layout, parent, false))
        else
            return RecHolder(LayoutInflater.from(con).inflate(R.layout.rec_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].mobile == AppInfo.mobile)
            (holder as SentHolder).show(list[position].message)
        else
            (holder as RecHolder).show(list[position].message)


    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].mobile == AppInfo.mobile)
            return 1
        else
            return 2
    }

    class SentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun show(msg: String)
        {
            var tv = itemView.findViewById<TextView>(R.id.msg_tv_se)
            tv.text = msg
        }
    }

    class RecHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun show(msg: String)
        {
            var tv = itemView.findViewById<TextView>(R.id.msg_tv_re)
            tv.text = msg
        }
    }

}