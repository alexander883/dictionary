package com.example.youwords.start

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.databinding.FragmentStartBinding
import java.util.*

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding?=null
    private lateinit var startviewmodel: StartViewModel
    private var  randomId:Int?=null
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

//////////////////обработка выбора позиции spinner (список секунд)
        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "1 c" -> {startviewmodel.setCountDownInterval(1_000)
                        startviewmodel.setItemSpinner(0)
                    }
                    "3 c" -> {startviewmodel.setCountDownInterval(3_000)
                        startviewmodel.setItemSpinner(1)
                    }
                    "5 c" -> {startviewmodel.setCountDownInterval(5_000)
                        startviewmodel.setItemSpinner(2)
                    }
                    "10 c" -> {startviewmodel.setCountDownInterval(10_000)
                        startviewmodel.setItemSpinner(3)
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
                if (isChecked) {
                    mytimer=startviewmodel.createTimer()
                    binding?.spinner?.isVisible=true
                    mytimer?.start()
                    startviewmodel.setFlagTimer(true)
                    startviewmodel.setEnableNext(false)
                }
                else{
                    mytimer?.onFinish()
                    startviewmodel.setEnableNext(true)
                    binding?.spinner?.isVisible=false
                    startviewmodel.setFlagTimer(false)
                }
        }
        // если словарь пуст. подсчитываем слова в словаре
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            startviewmodel.setSizeAll(size)
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
        startviewmodel.idRemainWord.observe(viewLifecycleOwner, Observer {
            startviewmodel.setSizeRead(it.size)
            if (it.isNotEmpty()) {//если не все слова показаны
                startviewmodel.getRandomId(it)//получаем случайный id слова из диапазона которое показываем
                if (startviewmodel.flagTimer.value==false){ //если переключаем слова по таймеру
                    startviewmodel.setEnableNext(true)      /// кнопку next блокируем
                }
                startviewmodel.setEnableRemember(true)
                binding?.checkBox?.isVisible=true
                //изменяем показывыемые слова
                randomId=startviewmodel.randomId.value
                startviewmodel.setFlagEnd(true)
                startviewmodel.wordById(startviewmodel.randomId.value).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        if (startviewmodel.flagNext.value!!  and startviewmodel.flagEnd.value!! and (it.id==randomId )) {
                            startviewmodel.setEnText(it.enWord)
                            startviewmodel.setRuText(it.ruWord)
                            startviewmodel.setFlagNext(false)
                            startviewmodel.setFlagEnd(false)
                        } }
                })
            }
            if(it.isEmpty() and !startviewmodel.dictionaryEmpty.value!!) {
                startviewmodel.setEnableRemember(false)
                startviewmodel.setEmptyText()
                binding?.checkBox?.isVisible=false
                binding?.checkBox?.isChecked=false
                binding?.spinner?.isVisible=false
                startviewmodel.setEnableNext(false)
            }
        })
        //получаем количество карточек
        startviewmodel.wordCard.observe(viewLifecycleOwner, Observer {
            it?.let {
                startviewmodel.setCountCard(it.size)
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

