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
import kotlin.properties.Delegates

///!!!!!!!!!!
class WordsReadFragment : Fragment() {
    private var binding: FragmentWordsreadBinding?=null
    private lateinit var wordsreadviewmodel: WordsReadViewModel
    private var dictionary_empty=true
    private var flag_next=true
    private var  random_id:Int?=null
    private var flag_end=false// флаг окончания показа карточек(чтобы не показывать последнюю)
    var init=true
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
       // binding?.ruText?.text=wordsreadviewmodel.ruText.value
        // если словарь пуст. подсчитываем слова в словаре
        wordsreadviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            wordsreadviewmodel.setSize_All(size)
            if(it.isEmpty()){
                changeNext_off()
                changeReset_off()
                changeRemember_off()
            //    wordsreadviewmodel.setSize_Rem(0)
                //binding?.count?.text=(0).toString()
            }
            else{
                //changeNext_on()
                changeReset_on()
             //   changeRemember_on()
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
                    changeNext_on()
                    changeRemember_on()
                    // wordsreadviewmodel.setSize_Rem(it.size)//
                    //изменяем показывыемые слова
                     random_id=wordsreadviewmodel.random_id.value
                    Log.i("LOG", "получили рандом  id=$random_id")
                    flag_end=true


                    wordsreadviewmodel.word_by_id(wordsreadviewmodel.random_id.value).observe(viewLifecycleOwner, Observer {
                            // val r=wordsreadviewmodel.random_id.value
                          //  Log.i("LOG", "внутри word_by_id $random_id")

                            it?.let {
                                if (flag_next  and flag_end and (it.id==random_id )) {
                                    Log.i("LOG", " устанавливаемый id=${it.id}")
                                    wordsreadviewmodel.set_enText(it.enWord)
                                    wordsreadviewmodel.set_ruText(it.ruWord)
                                    //binding?.ruText?.text=it.ruWord
                                    var r=wordsreadviewmodel.random_id.value
                                    Log.i("LOG", "изменили текст id=$r")
                                    val o=wordsreadviewmodel.ruText.value
                                    Log.i("LOG", " rutext $o")
                                    flag_next = false
                                    flag_end=false
                                } }

                        })


                }
            if(it.isEmpty() and !dictionary_empty) {
                  //  binding?.count?.text = "0"

                    changeNext_off()
                      changeRemember_off()
                    Toast.makeText(requireContext(), "Вы запомнили все слова", Toast.LENGTH_LONG)
                        .show()
                wordsreadviewmodel.set_enText("empty")
                wordsreadviewmodel.set_ruText("пустой")

                }
          //  }


        })
        //получаем количество карточек
        wordsreadviewmodel.word_notremember.observe(viewLifecycleOwner, Observer {
           it?.let { wordsreadviewmodel.set_countCard(it.size) }
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



  //  private fun getRandom_id():Int{
      //  return wordsreadviewmodel.get_Random_id()
  //  }


}
