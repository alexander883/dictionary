package com.example.youwords.allwords

import android.os.Bundle
import android.provider.UserDictionary
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.adapter_all_words.AllWordsAdapter
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAllWordsBinding




class AllWordsFragment : Fragment(), AllWordsAdapter.OnItemClickListener {
    private var binding: FragmentAllWordsBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    val adapter = AllWordsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   val k=requireContext()
        allwordsviewmodel = ViewModelProvider(this).get(AllWordsViewModel::class.java)
        val fragmentBinding = FragmentAllWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            allWordsViewModel=allwordsviewmodel
            allwordsFragment=this@AllWordsFragment
            allWordsList.adapter=adapter
        }


         allwordsviewmodel.allWords.observe(viewLifecycleOwner, Observer {
              allwordsviewmodel.getSize(it.size)
              adapter.data=it
        })


    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "position clicked", Toast.LENGTH_SHORT).show()
     //   val clickedItem = Words[position]
    //    clickedItem.text1 = "Clicked"
      //  adapter.notifyItemChanged(position)
    }

}