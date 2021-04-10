package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private lateinit var wordviewmodel: WordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.words?.setOnClickListener { go2_to_wordsFragment()}
        binding?.addWord?.setOnClickListener {addWord()}

    }
    fun addWord(){
        findNavController().navigate(R.id.action_startFragment_to_addWordFragment)
    }
    fun go2_to_wordsFragment(){

        findNavController().navigate(R.id.action_startFragment_to_wordsFragment)
    }

}