package com.example.youwords.addword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.main_activity.ActivityInterractor
import com.example.youwords.allwords.AllWordsViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAddwordBinding


class AddWordFragment : Fragment() {
    private var binding: FragmentAddwordBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    private var transfer: ActivityInterractor?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
            context as ActivityInterractor
            transfer=context
    }

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
            addwordFragment=this@AddWordFragment
        }
    }

    fun insertWord() {// проверяем заполненность
        val enWord = binding?.enEditText?.text.toString().trim()
        val ruWord = binding?.rusEditText?.text.toString().trim()
        if (enWord.isEmpty() or ruWord.isEmpty()) {
            val toast=Toast.makeText(requireContext(), R.string.empty_field, Toast.LENGTH_LONG)
                toast.show()
        }
        else {
            val word = Words(id = 0, enWord = enWord, ruWord = ruWord)
                     allwordsviewmodel.addWord(word)
                     Toast.makeText(requireContext(), "$enWord добавлено", Toast.LENGTH_LONG).show()
                     findNavController().navigate(R.id.action_addWordFragment_to_startFragment)
                     transfer?.transferOnAddWordFragment()
        }
    }
}



