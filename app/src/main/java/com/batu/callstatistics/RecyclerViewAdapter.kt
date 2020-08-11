package com.batu.callstatistics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batu.callstatistics.database.item.CardObject
import com.batu.callstatistics.database.item.CardStatus
import com.batu.callstatistics.database.item.DataType

class RecyclerViewAdapter (var dataset: ArrayList<CardObject>, var context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var textTitle: TextView = linearLayout.findViewById(R.id.textViewTitle)
        var textName: TextView = linearLayout.findViewById(R.id.textViewName)
        var textDuration: TextView = linearLayout.findViewById(R.id.textViewDurationValue)
        var textCallCount: TextView = linearLayout.findViewById(R.id.textViewCallCountValue)
        var textInfo: TextView = linearLayout.findViewById(R.id.textViewInformation)
        var button: Button = linearLayout.findViewById(R.id.button_expand)

        val solidDuration: TextView = linearLayout.findViewById(R.id.textViewDuration)
        val solidCallCount: TextView = linearLayout.findViewById(R.id.textViewCallCount)
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
        //TODO: Fix those nasty warnings
        holder.textName.text = dataset[position].fullName
        holder.textDuration.text = dataset[position].totalDuration.toString() + " Seconds"
        holder.textCallCount.text = dataset[position].callCount.toString() + " Times"
        holder.textTitle.text = dataset[position].type.toString()

        // Fill the info area regarding to data type
        when (dataset[position].type){
            DataType.CALL_COUNT -> holder.textInfo.text = holder.textCallCount.text
            DataType.DURATION -> holder.textInfo.text = holder.textDuration.text
        }

        hideExtraData(holder)
        var cardStatus = CardStatus.COLLAPSED

        //TODO: Add animation, it looks pretty rough without animation. Make it smooth
        holder.button.setOnClickListener {
            if(cardStatus == CardStatus.COLLAPSED){
                showExtraData(holder)
                cardStatus = CardStatus.EXPANDED
                holder.button.text = "collapse"
            } else {
                hideExtraData(holder)
                cardStatus = CardStatus.COLLAPSED
                holder.button.text = "expand"
            }
        }
    }

    private fun hideExtraData(holder: ViewHolder){
        holder.textCallCount.visibility = View.GONE
        holder.textDuration.visibility = View.GONE
        holder.solidDuration.visibility = View.GONE
        holder.solidCallCount.visibility = View.GONE
    }

    private fun showExtraData(holder: ViewHolder){
        holder.textCallCount.visibility = View.VISIBLE
        holder.textDuration.visibility = View.VISIBLE
        holder.solidDuration.visibility = View.VISIBLE
        holder.solidCallCount.visibility = View.VISIBLE
    }



}