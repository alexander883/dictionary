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

///!!!!!!!!!!
class WordsReadFragment : Fragment() {
    private var binding: FragmentWordsreadBinding?=null
    private lateinit var wordsreadviewmodel: WordsReadViewModel
    private var init=true
    private var change=true
    private var change2=true
    private var flag_next=true
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
                changeNext_off()
                changeReset_off()
                changeRemember_off()
            //    wordsreadviewmodel.setSize_Rem(0)

            }
            else{
                //changeNext_on()
                changeReset_on()
                changeRemember_on()

                wordsreadviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
                    Log.i("LOG","шлавный observe")
                    val list_id=it


                    if (!list_id.isEmpty()) {
                        Log.i("LOG","IFF")

                        changeNext_on()
                        changeRemember_on()
                        wordsreadviewmodel.get_Random_id(list_id)//получаем случайный id слова из диапазона которое показываем
                       // wordsreadviewmodel.setSize_Rem(it.size)//
                        binding?.count?.text=(list_id.size-1).toString()

                     //   if (flag_next) {
                            Log.i("LOG", "IF")

                        if (init) { Log.i("LOG", "If $init")
                            wordsreadviewmodel.word_by_id(wordsreadviewmodel.random_id.value)
                                .observe(viewLifecycleOwner, Observer {
                                    //   if (flag_next) {
                                    binding?.enText?.text = it.enWord
                                    binding?.ruText?.text = it.ruWord
                                    flag_next = false
                                    Log.i("LOG", "изменили текст")
                                    //   }
                                    init=false
                                })

                        }
//}






                        binding?.buttonRemember?.setOnClickListener {
                            wordsreadviewmodel.updateRemember(wordsreadviewmodel.random_id.value!!)
                            wordsreadviewmodel.updateRead(wordsreadviewmodel.random_id.value!!)
                            flag_next=true
                            Log.i("LOG", "нажли запомнил")

                        }


                        binding?.buttonNext?.setOnClickListener {
                            Log.i("LOG","нажали next1")
                            wordsreadviewmodel.updateRead(wordsreadviewmodel.random_id.value!!)
                            Log.i("LOG","нажали next2")
                            flag_next=true

                        }

                    }
                    else   {binding?.count?.text="0"
                        changeNext_off()
                        //  changeRemember_off()
                        Toast.makeText(requireContext(), "Вы запомнили все слова", Toast.LENGTH_LONG).show()


                    }

                    binding?.buttonReset?.setOnClickListener {
                        wordsreadviewmodel.updateAll_Read()
                        Log.i("LOG", "нажали reset")
                    }

                })







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
        wordsreadviewmodel.updateAll_Read()
        changeNext_on()
        changeRemember_on()
        init=true
    }
    fun clickRemember(){
        Toast.makeText(requireContext(), "Запомнил", Toast.LENGTH_LONG).show()
        wordsreadviewmodel.updateRemember(wordsreadviewmodel.random_id.value!!)
        changeRemember_off()
    }



    private fun  updateRead(){
        wordsreadviewmodel.updateRead(wordsreadviewmodel.random_id.value!!)
    }

    

  //  private fun getRandom_id():Int{
      //  return wordsreadviewmodel.get_Random_id()
  //  }
    private fun changeCount(){
      var count=wordsreadviewmodel.size_rem.value ?: 0
      if (count>0){
          count=count-1
      }
        binding?.count?.text=count.toString()
    }


}
