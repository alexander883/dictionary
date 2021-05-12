package com.example.youwords.allwords

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.UserDictionary
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R


import com.example.youwords.adapter_all_words.AllWordsAdapter
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAllWordsBinding


class AllWordsFragment : Fragment(), AllWordsAdapter.OnItemClickListener {
    private var binding: FragmentAllWordsBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    val adapter = AllWordsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allwordsviewmodel = ViewModelProvider(requireActivity()).get(AllWordsViewModel::class.java)
        val fragmentBinding = FragmentAllWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            allWordsViewModel=allwordsviewmodel
            allwordsFragment=this@AllWordsFragment
            allWordsList.adapter=adapter
        }


         allwordsviewmodel.allWords.observe(viewLifecycleOwner, Observer {
              allwordsviewmodel.getSize(it.size)
              adapter.data=it
        })
        //получаем количество карточек
        allwordsviewmodel.word_notremember.observe(viewLifecycleOwner, Observer {
            it?.let { allwordsviewmodel.set_countCard(it.size) }
        })
          }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem =adapter.data[position]
        allwordsviewmodel.getClickedWord(clickedItem)
        alertDialog(clickedItem)
    }

    ///alert dialog
   fun alertDialog(word:Words){
        lateinit var str_items_1: String
        if (word.remember==true)
        {  str_items_1="Добавить в карточки"}
        else{
             str_items_1="Удалить из карточек"
        }
         val items = arrayOf("Удалить из словаря",str_items_1 , "Редактировать")

         val builder = AlertDialog.Builder(requireContext())
         with(builder)
         {
             setTitle(word.enWord)
             setItems(items) { dialog, which ->
                 when(which){
                     0-> deletWord(word)
                     1->{if (word.remember==true)
                     {  setNotRemember(word)
                           }
                     else{
                        setRemember(word)
                     }}
                     2->{
                         val bundle = Bundle()
                         bundle.putInt("id", word.id)
                         findNavController().navigate(R.id.action_allWordsFragment_to_redactActivity, bundle)
                     }
                 }
             }
             show()
         }
     }
      private fun deletWord(word:Words){
                 allwordsviewmodel.deleteWord(word)
                 val en=word.enWord
                 Toast.makeText(requireContext(), "$en удален", Toast.LENGTH_SHORT).show()
      }
    private fun setRemember(word: Words){
        allwordsviewmodel.updateRemember(word.id)
    }
    private fun setNotRemember(word: Words){
        allwordsviewmodel.updateNotRemember(word.id)
    }
}

