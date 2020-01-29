package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.table_items.view.*

class OrderBookViewAdapter(val bids : ArrayList<ArrayList<Double>>, val asks : ArrayList<ArrayList<Double>>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.table_items, parent, false))
    }

    override fun getItemCount(): Int {
        return bids.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bid?.text = bids[position].get(0).toString()
        holder?.ask?.text = asks[position].get(0).toString()
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each order to
        val bid = view.bids
        val ask = view.asks
}

