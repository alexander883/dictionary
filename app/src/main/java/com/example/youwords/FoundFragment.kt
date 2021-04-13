package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentFoundBinding


class FoundFragment : Fragment() {
    private var binding: FragmentFoundBinding?= null
    private lateinit var wordviewmodel: WordViewModel
   // val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wordviewmodel = ViewModelProvider(this).get(WordViewModel::class.java)
        val fragmentBinding = FragmentFoundBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      //  val adapter = FoundAdapter()
      //  binding?.foundWordList?.adapter=adapter
        wordviewmodel.searh_list_words.observe(viewLifecycleOwner, Observer {
            // на этот фрагмент попадаем только если в List есть запись( не null)

           // it?.let {
           //     adapter.data = it
           // }
        })

    }
}