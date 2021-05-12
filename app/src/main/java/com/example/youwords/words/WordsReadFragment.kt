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
import com.example.youwords.R

import com.example.youwords.databinding.FragmentWordsreadBinding
import java.lang.Exception
import kotlin.properties.Delegates

///!!!!!!!!!!


class WordsReadFragment : Fragment() {
    private var binding: FragmentWordsreadBinding?=null
    private lateinit var wordsreadviewmodel: WordsReadViewModel
    private var dictionary_empty=true
    private var flag_next=true
    private var  random_id:Int?=null
    private var flag_end=false// флаг окончания показа карточек(чтобы не показывать последнюю)

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
            wordsreadviewmodel.setSize_All(size)
            if(it.isEmpty()){
               // changeNext_off()
              ////////  changeReset_off()
                    // wordsreadviewmodel.setEnableReset(false)
                wordsreadviewmodel.setEnableRemember(false)
                wordsreadviewmodel.setEnableReset(false)
                //changeRemember_off()
            }
            else{

             //   changeReset_on()
                wordsreadviewmodel.setEnableReset(true)
                dictionary_empty=false

            }
        })
/////////
        wordsreadviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
           // if (!dictionary_empty) {//если в словаре есть слова
                Log.i("LOG", "в словаре есть слова")
                //  val list_id=it
            wordsreadviewmodel.setSize_Read(it.size)
               // binding?.count?.text = (it.size).toString()

                if (!it.isEmpty()) {//если не все слова показаны
                    Log.i("LOG", "не все слова показаны")

                    wordsreadviewmodel.get_Random_id(it)//получаем случайный id слова из диапазона которое показываем
                    //changeNext_on()
                    wordsreadviewmodel.setEnableNext(true)
                    wordsreadviewmodel.setEnableRemember(true)
                    wordsreadviewmodel.setEnableReset(true)
                   // changeRemember_on()
                    // wordsreadviewmodel.setSize_Rem(it.size)//
                    //изменяем показывыемые слова
                     random_id=wordsreadviewmodel.random_id.value
                    Log.i("LOG", "получили рандом  id=$random_id")
                    flag_end=true


                    wordsreadviewmodel.word_by_id(wordsreadviewmodel.random_id.value).observe(viewLifecycleOwner, Observer {
                            it?.let {
                                if (flag_next  and flag_end and (it.id==random_id )) {
                                    Log.i("LOG", " устанавливаемый id=${it.id}")
                                    wordsreadviewmodel.set_enText(it.enWord)
                                    wordsreadviewmodel.set_ruText(it.ruWord)
                                    val r=wordsreadviewmodel.random_id.value
                                    Log.i("LOG", "изменили текст id=$r")
                                    val o=wordsreadviewmodel.ruText.value
                                    Log.i("LOG", " rutext $o")
                                    flag_next = false
                                    flag_end=false
                                } }

                        })


                }
                if(it.isEmpty() and !dictionary_empty) {
                  //  changeNext_off()

                      wordsreadviewmodel.setEnableNext(false)
                      wordsreadviewmodel.setEnableRemember(false)
                     // changeRemember_off()
                      wordsreadviewmodel.setEmpty_text()
                }
        })
        //получаем количество карточек
        wordsreadviewmodel.word_notremember.observe(viewLifecycleOwner, Observer {
           it?.let { wordsreadviewmodel.set_countCard(it.size)
           if (it.isEmpty()){
               wordsreadviewmodel.setEnableReset(false)
           }}
        })
    }

    fun clickReset(){
        wordsreadviewmodel.updateAll_Read()
        Log.i("LOG", "нажали reset")
        flag_next=true
    }
    fun clickRemember(){
        wordsreadviewmodel.updateRemember(wordsreadviewmodel.random_id.value!!)
        wordsreadviewmodel.updateRead(wordsreadviewmodel.random_id.value!!)
        flag_next=true
        Log.i("LOG", "нажли запомнил")
    }
    fun clickNext(){
        Log.i("LOG","нажали next")
        wordsreadviewmodel.updateRead(wordsreadviewmodel.random_id.value!!)
        flag_next=true
    }
}
