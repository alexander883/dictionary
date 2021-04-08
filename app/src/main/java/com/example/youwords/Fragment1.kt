package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.Fragment1Binding

//
class Fragment1 : Fragment() {
    private var binding: Fragment1Binding? = null
    private lateinit var wviewmodel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   val view = inflater.inflate(R.layout.fragment_1, container, false)

        wviewmodel = ViewModelProvider(this).get(WordViewModel::class.java)

         val fragmentBinding = Fragment1Binding.inflate(inflater, container, false)
           binding = fragmentBinding
           return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.button?.setOnClickListener { insertWord() }
    }


   //findNavController().navigate(R.id.action_fragment1_to_fragment2)

    private fun insertWord(){
    val en_word=binding?.editTextTextPersonName?.text.toString()
    val ru_word=binding?.editTextTextPersonName2?.text.toString()
        val word=Words(id,enWord = en_word,ruWord = ru_word)
        wviewmodel.addWord(word)
        Toast.makeText(requireContext(),  en_word, Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_fragment1_to_fragment2)
    }
}
