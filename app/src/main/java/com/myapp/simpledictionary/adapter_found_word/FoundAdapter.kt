package com.myapp.simpledictionary.adapter_found_word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.simpledictionary.R
import com.myapp.simpledictionary.data.Words

class FoundAdapter: RecyclerView.Adapter<FoundAdapter.ViewHolder>() {

    var data =  listOf<Words>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val enWord: TextView = itemView.findViewById(R.id.en_word)
        private val ruWord: TextView = itemView.findViewById(R.id.rus_word)
        private val posit: TextView =itemView.findViewById(R.id.posit)

        fun bind(item: Words, position: Int) {
            enWord.text =item.enWord
            ruWord.text = item.ruWord
            posit.text=(position+1).toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R. layout.found_words_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}