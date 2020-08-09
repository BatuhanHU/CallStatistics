package com.batu.callstatistics

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.batu.callstatistics.database.Call

class RecyclerViewAdapter (var dataset: ArrayList<Call>, var context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var textName: TextView
        var textVersion: TextView
        init {
            textName = linearLayout.findViewById(R.id.textViewName)
            textVersion = linearLayout.findViewById(R.id.textViewVersion)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        // create a new view
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cards_layout, parent, false)
                as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLayout)
    }

    override fun getItemCount() = dataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.textName.text = dataset[position].name
        //holder.textPhone.text = dataset[position].phoneNumber

//        holder.itemView.setOnClickListener {
//            showAlertDialog(dataset[position].phoneNumber, dataset[position].name, position)
//        }
    }



}