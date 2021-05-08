package com.example.youwords.words

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.youwords.databinding.FragmentWordsreadBinding
import java.lang.Exception


class WordsReadFragment : Fragment() {
    private var binding: FragmentWordsreadBinding?=null
    private lateinit var wordsreadviewmodel: WordsReadViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wordsreadviewmodel = ViewModelProvider(this).get( WordsReadViewModel::class.java)
        val fragmentBinding = FragmentWordsreadBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordsReadViewModel=wordsreadviewmodel
            wordsreadFragment = this@WordsReadFragment
        }
        // для подсчета всех слов в словаре
        wordsreadviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            wordsreadviewmodel.getSize(size)
        })


        binding?.buttonReset?.setOnClickListener {
            wordsreadviewmodel.updateAll_Read()
            binding?.next?.setEnabled(true)
        }


        wordsreadviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "!!!!!!!!!", Toast.LENGTH_LONG).show()
it?.let {
    Log.i("LOG", "$it it")
    Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()}
      try{

            wordsreadviewmodel.getList_id(it)
            changeEnable()
            changeText()
            changeCount()


            binding?.next?.setOnClickListener {
                changeEnable()
                // g()
                //  binding?.next?.setEnabled(false)
                changeText()
                changeCount()
                update()
            }
            binding?.buttonRemember?.setOnClickListener {
                Toast.makeText(requireContext(), "Запомнил", Toast.LENGTH_LONG).show()
                wordsreadviewmodel.updateRemember(wordsreadviewmodel.random.value!!)
            }

      }
          catch (e:Exception){
               Toast.makeText(requireContext(), "Вы запомнили все слова", Toast.LENGTH_LONG).show()
               binding?.buttonRemember?.setEnabled(false)
                binding?.next?.setEnabled(false)
           }



        })
    }

    private fun changeEnable(){//если кончились слова блокируем кнопку next
        if (wordsreadviewmodel.getSizeList() == 1) {
            binding?.next?.setEnabled(false)
        }
    }


    private fun  update(){
        wordsreadviewmodel.updateRead(wordsreadviewmodel.random.value!!)

    }
    private  fun changeText(){



        wordsreadviewmodel.word_by_id(getRandom_id()).observe(viewLifecycleOwner, Observer {
           it?.let {
               binding?.enText?.text = it.enWord
               binding?.ruText?.text = it.ruWord }
        })
    }
    

    private fun getRandom_id():Int{
        return wordsreadviewmodel.get_Random_id()
    }
    private fun changeCount(){
        binding?.count?.text=wordsreadviewmodel.getSizeList().toString()
        //if (wordviewmodel.getSizeList()){

    }


}
