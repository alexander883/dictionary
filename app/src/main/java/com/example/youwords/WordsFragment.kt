package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentWordsBinding
import kotlinx.coroutines.flow.map


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


        //получаем список id
     wordviewmodel.allWords.observe(viewLifecycleOwner, Observer{
         getList_ids(it)
         val k=it[2]
         Toast.makeText(requireContext(),  "$k", Toast.LENGTH_LONG).show()
     })

        wordviewmodel.ranWord.observe(viewLifecycleOwner, Observer {
            binding?.enText?.text=it.enWord.toString()
            binding?.ruText?.text=it.ruWord
        })

    }

        // получаем случайное id
    // получаем случайный индекс, удаляем по индексу
     private fun getList_ids(it:List<Int>){
          wordviewmodel.get_Random_id(it)
      }

    }


