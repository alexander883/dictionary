package com.example.youwords.start

import android.content.Context
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

const val MY_CONST = "something"
class StartFragment : Fragment() {
    private var binding: FragmentStartBinding?=null
    private lateinit var startviewmodel: StartViewModel
    private var  random_id:Int?=null

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
        ///////////выпадающий список, задающий время
        val timeList= arrayOf("1 c", "3 c", "5 c", "10 c")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeList)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel=startviewmodel
            startFragment = this@StartFragment
            spinner.adapter = adapter
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



//////////////////////////////////////////////////////////////////






//////////////////обработка выбора позиции spinner (список секунд)
        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "1 c" -> {startviewmodel.setTime(1000)
                    }
                    "3 c" -> {startviewmodel.setTime(3000)
                    }
                    "5 c" -> {startviewmodel.setTime(5000)
                    }
                    "10 c" -> {startviewmodel.setTime(10000)

                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



///////////////////показываем спинер при включении checkBox

        binding?.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked==true) {
                binding?.spinner?.isVisible=true
                startviewmodel.myTimer.start()
                Toast.makeText(requireContext(), "checl", Toast.LENGTH_SHORT).show()
            }
            else{

                binding?.spinner?.isVisible=false
            }
        }

        // если словарь пуст. подсчитываем слова в словаре
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            startviewmodel.setSize_All(size)
            if(it.isEmpty()){
                startviewmodel.setEnableRemember(false)
            }
            else{
                startviewmodel.setDictionaryEmpty(false)
            }
        })
/////////
        startviewmodel.all_id_read_not_remember.observe(viewLifecycleOwner, Observer {
            // if (!dictionary_empty) {//если в словаре есть слова
            Log.i("LOG", "в словаре есть слова")
            //  val list_id=it
            startviewmodel.setSize_Read(it.size)
            if (!it.isEmpty()) {//если не все слова показаны
                Log.i("LOG", "не все слова показаны")

                startviewmodel.get_Random_id(it)//получаем случайный id слова из диапазона которое показываем
                //changeNext_on()
                startviewmodel.setEnableNext(true)
                startviewmodel.setEnableRemember(true)

                //изменяем показывыемые слова
                random_id=startviewmodel.random_id.value
                Log.i("LOG", "получили рандом  id=$random_id")
                startviewmodel.setFlagEnd(true)


                startviewmodel.word_by_id(startviewmodel.random_id.value).observe(viewLifecycleOwner, Observer {
                    it?.let {
                    //    if (flag_next  and flag_end and (it.id==random_id )) {
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
////////////////////////////////нажатия кнопок//////////////////////
    fun clickReset(){
        startviewmodel.reset()
    }
    fun clickRemember(){
        startviewmodel.remember()
    }
    fun clickNext(){
        Log.i("LOG","нажали next")
        startviewmodel.updateRead(startviewmodel.random_id.value!!)
        startviewmodel.setFlagNext(true)
    }



}