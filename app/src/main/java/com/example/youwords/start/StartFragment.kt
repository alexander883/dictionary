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



class StartFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var binding: FragmentStartBinding?=null
    private lateinit var startviewmodel: StartViewModel
    private var  random_id:Int?=null






    inner class Timer(val context: Context) : CountDownTimer(20000, 1000){
        var count=0
        var init=0
        override fun onTick(millisUntilFinished: Long) {
            count = (millisUntilFinished / 1000).toInt()

            if (init>1) {
                 clickNext()
            }
            init += 1

            //// Toast.makeText(context, "EEEE $count", Toast.LENGTH_SHORT).show()
        }

        override fun onFinish() {
            // super.cancel()
        }
    }






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

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeList)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            startViewModel=startviewmodel
            startFragment = this@StartFragment
            spinner.adapter = adapter
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        //  binding?. spinner?.adapter = adapter
        binding?.switch1?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked==true)
            {
                binding?.spinner?.isVisible=true
                Toast.makeText(requireContext(), "checl", Toast.LENGTH_SHORT).show()
        }
            else{binding?.spinner?.isVisible=false
                Toast.makeText(requireContext(), "NOOO", Toast.LENGTH_SHORT).show()

            }            }











        val timer=Timer(requireContext())
            //   timer.start()










        // если словарь пуст. подсчитываем слова в словаре
        startviewmodel.allWords.observe(viewLifecycleOwner, Observer {
            val size=it?.size ?: 0
            startviewmodel.setSize_All(size)
            if(it.isEmpty()){
                startviewmodel.setEnableRemember(false)
            }
            else{
                startviewmodel.setDictionaryEmpty(false)
              //  dictionary_empty=false
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
                //flag_end=true
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
                          //  flag_next = false
                           // flag_end=false
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
        ///////////выпадающий список, задающий время

        //////обработка выбора позиции spinner (список секунд)
        /*
        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

             //   val selectedItem = parent.getItemAtPosition(position).toString()
             //   if (selectedItem == "1 c") {
              //      Toast.makeText(requireContext(), "!!", Toast.LENGTH_SHORT).show()
               // }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
*/
    }

    fun clickReset(){
        startviewmodel.updateAll_Read()
        Log.i("LOG", "нажали reset")
        startviewmodel.setFlagNext(true)
       // flag_next=true
    }
    fun clickRemember(){
        startviewmodel.updateRemember(startviewmodel.random_id.value!!)
        startviewmodel.updateRead(startviewmodel.random_id.value!!)
        startviewmodel.setFlagNext(true)
      //  flag_next=true
        Log.i("LOG", "нажли запомнил")
    }
    fun clickNext(){
        Log.i("LOG","нажали next")
        startviewmodel.updateRead(startviewmodel.random_id.value!!)
        startviewmodel.setFlagNext(true)
       // flag_next=true
    }
    val timeList= arrayOf("1 c", "3 c")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          val selectedItem = parent?.getItemAtPosition(position).toString()
           if (selectedItem == "1 c") {
              Toast.makeText(requireContext(), "!!", Toast.LENGTH_SHORT).show()
         }
        if (selectedItem == "3 c") {
            Toast.makeText(requireContext(), "!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}