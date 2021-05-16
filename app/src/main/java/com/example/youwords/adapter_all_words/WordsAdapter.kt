package com.example.youwords.adapter_all_words


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youwords.R
import com.example.youwords.data.Words

class AllWordsAdapter( private  val listener: OnItemClickListener
): RecyclerView.Adapter<AllWordsAdapter.ViewHolder>() {

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
        val itemView = LayoutInflater.from(parent.context).inflate(R. layout.all_words_item, parent, false)
        return ViewHolder(itemView)
    }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
       val en_word: TextView = itemView.findViewById(R.id.en_word)
       val ru_word: TextView = itemView.findViewById(R.id.rus_word)
       val posit: TextView = itemView.findViewById(R.id.posit)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
        fun bind(item: Words, position: Int) {
            en_word.text = item.enWord
            if (!item.remember) {
                en_word.setTextColor(Color.RED)
                ru_word.setTextColor(Color.RED)
            }
            /////////////////////
            if (item.remember) {
                en_word.setTextColor(Color.BLACK)
                ru_word.setTextColor(Color.BLACK)
                ///////////////////////
            }
            ru_word.text = item.ruWord
            posit.text=(position+1).toString()
        }

}
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}

