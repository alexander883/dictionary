package com.example.youwords.addword

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.redact_activity.ActivityInterractor
import com.example.youwords.allwords.AllWordsViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAddwordBinding


class AddWordFragment : Fragment() {
    private var binding: FragmentAddwordBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    private var transfer:ActivityInterractor?=null

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
        val en_word = binding?.enEditText?.text.toString().trim()
        val ru_word = binding?.rusEditText?.text.toString().trim()
        if (en_word.isEmpty() or ru_word.isEmpty()) {
            val toast=Toast.makeText(requireContext(), R.string.empty_field, Toast.LENGTH_LONG)
               // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
        }
        else {
            val word = Words(id = 0, enWord = en_word, ruWord = ru_word)
                     allwordsviewmodel.addWord(word)
                     Toast.makeText(requireContext(), "$en_word добавлено", Toast.LENGTH_LONG).show()
                     findNavController().navigate(R.id.action_addWordFragment_to_startFragment)
                     transfer?.transferOnAddWordFragment()
         }
        }
    private fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    }



