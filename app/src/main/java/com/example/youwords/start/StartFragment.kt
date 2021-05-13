package com.example.youwords.start

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.databinding.FragmentStartBinding


class timer(val context: Context) : CountDownTimer(20000, 1000){
    // val c=context
    var count=0
    override fun onTick(millisUntilFinished: Long) {
        count = (millisUntilFinished / 1000).toInt()
        //// Toast.makeText(context, "EEEE $count", Toast.LENGTH_SHORT).show()
    }

    override fun onFinish() {
        // super.cancel()
    }
}
//   val t=timer(this)
//  t.start()
// t.onFinish()

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding?=null
    private lateinit var startviewmodel: StartViewModel
    private var dictionary_empty=true
    private var flag_next=true
    private var  random_id:Int?=null
    private var flag_end=false// флаг окончания показа карточек(чтобы не показывать последнюю)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startviewmodel = ViewModelProvider(this).get(StartViewModel::class.java)
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel=startviewmodel
            startFragment = this@StartFragment
        }


        // если словарь пуст. подсчитываем слова в словаре
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            startviewmodel.setSize_All(size)
            if(it.isEmpty()){
                startviewmodel.setEnableRemember(false)
            }
            else{
                dictionary_empty=false
            }
        })
/////////
        startviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
            // if (!dictionary_empty) {//если в словаре есть слова
            Log.i("LOG", "в словаре есть слова")
            //  val list_id=it
            startviewmodel.setSize_Read(it.size)
            // binding?.count?.text = (it.size).toString()
           // startviewmodel.setEnableReset(true)
            if (!it.isEmpty()) {//если не все слова показаны
                Log.i("LOG", "не все слова показаны")

                startviewmodel.get_Random_id(it)//получаем случайный id слова из диапазона которое показываем
                //changeNext_on()
                startviewmodel.setEnableNext(true)
                startviewmodel.setEnableRemember(true)

                //изменяем показывыемые слова
                random_id=startviewmodel.random_id.value
                Log.i("LOG", "получили рандом  id=$random_id")
                flag_end=true


                startviewmodel.word_by_id(startviewmodel.random_id.value).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        if (flag_next  and flag_end and (it.id==random_id )) {
                            Log.i("LOG", " устанавливаемый id=${it.id}")
                            startviewmodel.set_enText(it.enWord)
                            startviewmodel.set_ruText(it.ruWord)
                            val r=startviewmodel.random_id.value
                            Log.i("LOG", "изменили текст id=$r")
                            flag_next = false
                            flag_end=false
                        } }
                })
            }
            if(it.isEmpty() and !dictionary_empty) {
                startviewmodel.setEnableNext(false)
                startviewmodel.setEnableRemember(false)
                startviewmodel.setEmpty_text()
            }
        })
        //получаем количество карточек
        startviewmodel.word_notremember.observe(viewLifecycleOwner, Observer {
            it?.let {
                startviewmodel.set_countCard(it.size)
                if (it.isEmpty()){startviewmodel.setEnableReset(false)}
                else {startviewmodel.setEnableReset(true)}
               }
        })
    }

    fun clickReset(){
        startviewmodel.updateAll_Read()
        Log.i("LOG", "нажали reset")
        flag_next=true
    }
    fun clickRemember(){
        startviewmodel.updateRemember(startviewmodel.random_id.value!!)
        startviewmodel.updateRead(startviewmodel.random_id.value!!)
        flag_next=true
        Log.i("LOG", "нажли запомнил")
    }
    fun clickNext(){
        Log.i("LOG","нажали next")
        startviewmodel.updateRead(startviewmodel.random_id.value!!)
        flag_next=true
    }
}