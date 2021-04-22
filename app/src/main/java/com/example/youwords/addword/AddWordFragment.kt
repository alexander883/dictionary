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
import com.example.youwords.allwords.AllWordsViewModel
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAddwordBinding


class AddWordFragment : Fragment() {
    private var binding: FragmentAddwordBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allwordsviewmodel = ViewModelProvider(requireActivity()).get(AllWordsViewModel::class.java)
        val fragmentBinding = FragmentAddwordBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            allWordsViewModel=allwordsviewmodel
         //   allWordsViewModel=allWordsViewModel
            addwordFragment=this@AddWordFragment
        }
                // если перешли для редактирования
        allwordsviewmodel.clickedWord.value?.let {
            binding?.buttonAddWord?.text="Отредактировать"
            binding?.enEditText?.setText(it.enWord)
            binding?.rusEditText?.setText(it.ruWord)

        }

    }
    fun insertWord() {// проверяем заполненность
        val en_word = binding?.enEditText?.text.toString()
        val ru_word = binding?.rusEditText?.text.toString()
        if (en_word.isEmpty() or ru_word.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
        }
        else {
           val k= allwordsviewmodel.clickedWord.value?.let {
                val w = Words(it.id, en_word, ru_word, it.read, it.remember)
                allwordsviewmodel.updateRedact(w)
                findNavController().navigate(R.id.action_addWordFragment_to_allWordsFragment)

            }
            if (k==null){
                val word = Words(id = 0, enWord = en_word, ruWord = ru_word)
                     allwordsviewmodel.addWord(word)
                     Toast.makeText(requireContext(), en_word, Toast.LENGTH_LONG).show()
                     findNavController().navigate(R.id.action_addWordFragment_to_startFragment)}
         }
        }
    }



