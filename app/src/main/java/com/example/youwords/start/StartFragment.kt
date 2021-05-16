package com.example.youwords.start

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.databinding.FragmentStartBinding
import java.util.*
import kotlin.concurrent.timer

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding?=null
    private lateinit var startviewmodel: StartViewModel
    private var  random_id:Int?=null
    private var mytimer: CountDownTimer?= null
    private var prefs: SharedPreferences?=null

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
        ///файл с хранимыми настройками
        prefs=activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        prefs?.let{if(it.contains("saveItemSpinner")){
            // Получаем item spinner из настроек
           val item = it.getInt("saveItemSpinner", 0)
           startviewmodel.setItemSpinner(item)
        }}
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, startviewmodel.timeList.value!!)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel=startviewmodel
            startFragment = this@StartFragment
            spinner.adapter = adapter
            spinner.setSelection(startviewmodel.itemSpinner.value!!)
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


//////////////////////////////////////////////////////////////////


//////////////////обработка выбора позиции spinner (список секунд)
        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "1 c" -> {startviewmodel.setCountDownInterval(1_000)
                        Toast.makeText(requireContext(), "1c", Toast.LENGTH_SHORT).show()
                        startviewmodel.setItemSpinner(0)
                    }
                    "3 c" -> {startviewmodel.setCountDownInterval(3_000)
                        startviewmodel.setItemSpinner(1)
                        Toast.makeText(requireContext(), "3c", Toast.LENGTH_SHORT).show()
                    }
                    "5 c" -> {startviewmodel.setCountDownInterval(5_000)
                        startviewmodel.setItemSpinner(2)
                        Toast.makeText(requireContext(), "5c", Toast.LENGTH_SHORT).show()
                    }
                    "10 c" -> {startviewmodel.setCountDownInterval(10_000)
                        startviewmodel.setItemSpinner(3)
                        Toast.makeText(requireContext(), "10c", Toast.LENGTH_SHORT).show()

                    }
                }
                resetTimer()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


///////////////////показываем спинер при включении checkBox
               //////включаем/выключаем таймер
        binding?.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked==true ) {
                    mytimer=startviewmodel.createTimer()
                    binding?.spinner?.isVisible=true
                    mytimer?.start()
                   // startviewmodel.setEnableNext(false)
                }
                else{
                    mytimer?.onFinish()
                    binding?.spinner?.isVisible=false
                   // startviewmodel.setEnableNext(true)
                }
        }

        // если словарь пуст. подсчитываем слова в словаре
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            startviewmodel.setSize_All(size)
            if(it.isEmpty()){
                startviewmodel.setEnableRemember(false)
                binding?.checkBox?.isVisible=false
                binding?.checkBox?.isChecked=false
                binding?.spinner?.isVisible=false
            }
            else{
                startviewmodel.setDictionaryEmpty(false)
            }
        })
/////////
        startviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
            Log.i("LOG", "в словаре есть слова")
            //  val list_id=it
            startviewmodel.setSize_Read(it.size)
            if (!it.isEmpty()) {//если не все слова показаны
                Log.i("LOG", "не все слова показаны")

                startviewmodel.get_Random_id(it)//получаем случайный id слова из диапазона которое показываем
                //changeNext_on()
                startviewmodel.setEnableNext(true)
                startviewmodel.setEnableRemember(true)
                binding?.checkBox?.isVisible=true
                //


                //изменяем показывыемые слова
                random_id=startviewmodel.random_id.value
                Log.i("LOG", "получили рандом  id=$random_id")
                startviewmodel.setFlagEnd(true)


                startviewmodel.word_by_id(startviewmodel.random_id.value).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        if (startviewmodel.flag_next.value!!  and startviewmodel.flag_end.value!! and (it.id==random_id )) {
                            Log.i("LOG", " устанавливаемый id=${it.id}")
                            startviewmodel.set_enText(it.enWord)
                            startviewmodel.set_ruText(it.ruWord)
                            val r=startviewmodel.random_id.value
                            Log.i("LOG", "изменили текст id=$r")
                            startviewmodel.setFlagNext(false)
                            startviewmodel.setFlagEnd(false)
                        } }
                })
            }
            if(it.isEmpty() and !startviewmodel.dictionary_empty.value!!) {
                startviewmodel.setEnableNext(false)
                startviewmodel.setEnableRemember(false)
                startviewmodel.setEmpty_text()
                binding?.checkBox?.isVisible=false
                binding?.checkBox?.isChecked=false
                binding?.spinner?.isVisible=false

            }
        })
        //получаем количество карточек
        startviewmodel.word_notremember.observe(viewLifecycleOwner, Observer {
            it?.let {
                startviewmodel.set_countCard(it.size)
                if (it.isEmpty()){startviewmodel.setEnableReset(false)
                    }
                else {startviewmodel.setEnableReset(true)}
               }
        })


    }
    private fun resetTimer(){
        mytimer?.let {
            it.onFinish()
            mytimer=startviewmodel.createTimer()
            mytimer?.start() }
    }
////////////////////////////////нажатия кнопок//////////////////////
    fun clickReset(){
        startviewmodel.reset()
    }
    fun clickRemember(){
        startviewmodel.remember()
    }
    fun clickNext(){
        Log.i("LOG","нажали next")
        startviewmodel.next()
    }
/////соханяем текущий выбор позиции spinner при остановки фрагмента
    @SuppressLint("CommitPrefEdits")
    override fun onStop() {
        super.onStop()
        val editor=prefs?.edit()
        editor?.putInt("saveItemSpinner", startviewmodel.itemSpinner.value!!)?.apply()
    }
}

