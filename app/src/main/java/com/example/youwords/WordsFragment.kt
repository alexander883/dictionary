package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentWordsBinding


class WordsFragment : Fragment() {
    private var binding: FragmentWordsBinding?=null
    private lateinit var wordviewmodel: WordViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wordviewmodel = ViewModelProvider(this).get(WordViewModel::class.java)
        val fragmentBinding = FragmentWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     wordviewmodel.allWords.observe(viewLifecycleOwner, Observer{
         wordviewmodel.g(requireContext()) })

        }

        // val list_id= wviewmodel.allId
     //   val t=list_id.get(1)
     //   Toast.makeText(requireContext(),  "$t", Toast.LENGTH_LONG).show()
        //binding?.w?.setOnClickListener { findNavController().navigate(R.id.action_fragment2_to_fragment1) }

    }


