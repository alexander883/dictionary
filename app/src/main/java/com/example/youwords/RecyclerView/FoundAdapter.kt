package com.example.youwords.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.youwords.data.Words
import com.example.youwords.R

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class FoundAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data =  listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.toString()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.word_item, parent, false) as TextView
        Log.i("h","onCreateViewHolde")
        return TextItemViewHolder(view)

    }

}
