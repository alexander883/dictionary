package com.example.youwords.start

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.databinding.FragmentStartBinding
import android.widget.Toast
import com.example.youwords.R
import com.example.youwords.data.WordViewModel

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private lateinit  var startviewmodel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        startviewmodel = ViewModelProvider(this).get(StartViewModel::class.java)
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel=startviewmodel
            startFragment=this@StartFragment

        }
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            try {it[0]
               binding?.buttonSearch?.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_searchFragment) }
                binding?.buttonToAllWords?.setOnClickListener {  findNavController().navigate(R.id.action_startFragment_to_allWordsFragment) }
                binding?.buttonWords?.setOnClickListener {  findNavController().navigate(R.id.action_startFragment_to_wordsReadFragment) }
            }
            catch (e:Exception){
                Toast.makeText(requireContext(), "Словарь пуст!", Toast.LENGTH_LONG).show()
            }
        } )

    }
        fun addWord(){
        findNavController().navigate(R.id.action_startFragment_to_addWordFragment)
        }




}