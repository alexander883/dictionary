package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.RecyclerView.FoundAdapter
import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentFoundBinding
import com.google.android.material.snackbar.Snackbar


class FoundFragment : Fragment() {
    private var binding: FragmentFoundBinding?= null
    private lateinit var wordviewmodel: WordViewModel
    val adapter = FoundAdapter()

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
        super.onViewCreated(view, savedInstanceState)
        //binding?.wordList?.adapter=adapter
        binding?.button?.setOnClickListener {  // wordviewmodel.all_id.observe(viewLifecycleOwner, Observer {
            val list= wordviewmodel.mutablelist_list_id.value ?: listOf(1)

            adapter.data=list

          //  val h=it.count()
           // Toast.makeText(requireContext(), "$h", Toast.LENGTH_LONG).show()
     //   })
        }
binding?.buttoncc?.setOnClickListener {
    val b=wordviewmodel.mutablelist_list_id.value
    Toast.makeText(requireContext(),  "$b", Toast.LENGTH_LONG).show() }
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordViewModel=wordviewmodel
            foundFragment=this@FoundFragment
            wordList.adapter=adapter
          }

     }
}