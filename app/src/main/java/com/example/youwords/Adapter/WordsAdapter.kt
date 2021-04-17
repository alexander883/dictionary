package com.example.youwords.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youwords.R
import com.example.youwords.data.Words

class AllWordsAdapter: RecyclerView.Adapter<AllWordsAdapter.ViewHolder>() {

    var data =  listOf<Words>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val en_word: TextView = itemView.findViewById(R.id.en_word)
        val ru_word: TextView = itemView.findViewById(R.id.rus_word)


        fun bind(item: Words) {
            en_word.text = item.enWord
            ru_word.text = item.ruWord

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R. layout.all_words_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

