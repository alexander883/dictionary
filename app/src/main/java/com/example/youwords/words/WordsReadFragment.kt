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
    private var dictionary_empty=true
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
        // если словарь пуст. подсчитываем слова в словаре
        wordsreadviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            if(it.isEmpty()){
              //  dictionary_empty=true
                changeNext_off()
                changeReset_off()
                changeRemember_off()
            }
            else{
              //  dictionary_empty=false
                changeNext_on()
                changeReset_on()
                changeRemember_on()
                obs()

            }
            wordsreadviewmodel.setSize_All(size)
        })

    }

    fun obs(){
        wordsreadviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "!!!!!!!!!", Toast.LENGTH_LONG).show()

                if (!it.isEmpty()) {

                    Toast.makeText(context, "$it Вызываю", Toast.LENGTH_LONG).show()


                    wordsreadviewmodel.getList_id(it)
                    changeNext_on()
                    //changeText()
                    changeCount()
                    changeText()

                    //  binding?.buttonNext?.setOnClickListener {
                    // g()
                    //  binding?.next?.setEnabled(false)
                    //    changeText()
                    //     changeCount()
                    //     update()
                    //  }
                    binding?.buttonRemember?.setOnClickListener {
                        Toast.makeText(requireContext(), "Запомнил", Toast.LENGTH_LONG).show()
                        wordsreadviewmodel.updateRemember(wordsreadviewmodel.random.value!!)
                    }
                } else {


                    Toast.makeText(requireContext(),
                        "Вы запомнили все слова",
                        Toast.LENGTH_LONG
                    ).show()


                }



        })
    }
    private fun changeNext_off(){//если кончились слова блокируем кнопку next
            binding?.buttonNext?.setEnabled(false)
    }
    private fun changeNext_on(){
        binding?.buttonNext?.setEnabled(true)
    }
    private fun changeReset_off(){
        binding?.buttonReset?.setEnabled(false)
    }
    private fun changeReset_on(){
        binding?.buttonReset?.setEnabled(true)
    }
    private fun changeRemember_off(){
        binding?.buttonRemember?.setEnabled(false)
    }
    private fun changeRemember_on(){
        binding?.buttonRemember?.setEnabled(true)
    }

    fun clickReset(){

    }
    fun clickRemember(){

    }
    fun clickNext(){
        changeText()
        changeCount()
        update()
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
