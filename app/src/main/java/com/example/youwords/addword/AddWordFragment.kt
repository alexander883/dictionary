package com.example.youwords.addword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAddwordBinding


class AddWordFragment : Fragment() {
    private var binding: FragmentAddwordBinding? = null
    private lateinit var addviewmodel: AddViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addviewmodel = ViewModelProvider(this).get(AddViewModel::class.java)
        val fragmentBinding = FragmentAddwordBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            addViewModel=addviewmodel
            addwordFragment=this@AddWordFragment
        }

    }
    fun insertWord(){
        val en_word=binding?.editTextTextPersonName?.text.toString()
        val ru_word=binding?.editTextTextPersonName2?.text.toString()
        if (en_word.isEmpty() or ru_word.isEmpty()){
            Toast.makeText(requireContext(),  "Заполните все поля!", Toast.LENGTH_LONG).show()
        }
        else{
            val word=Words(id=0,enWord = en_word,ruWord = ru_word)
            addviewmodel.addWord(word)
            Toast.makeText(requireContext(),  en_word, Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addWordFragment_to_startFragment)}

    }
}

