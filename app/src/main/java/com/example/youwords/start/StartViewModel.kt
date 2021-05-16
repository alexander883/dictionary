package com.example.youwords.start

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel

class StartViewModel(application:Application) : WordViewModel(application)  {
    private val _random_id= MutableLiveData<Int>()//случайный id
    val random_id: LiveData<Int> =_random_id
    // количество всех слов в словаре
    private var _size_all = MutableLiveData<Int>()
    val size_all : LiveData<Int> = _size_all

    private var _size_read = MutableLiveData<Int>()
    val size_read : LiveData<Int> = _size_read

    private val _ruText= MutableLiveData<String>()
    var ruText: LiveData<String> =_ruText
    private val _enText= MutableLiveData<String>()
    var enText: LiveData<String> =_enText
    //
    private val _countCard= MutableLiveData<Int>()
    var countCard: LiveData<Int> =_countCard
    ///кнопки
    private val _enabledNext= MutableLiveData<Boolean>()
    var enabledNext: LiveData<Boolean> =_enabledNext
    private val _enabledReset= MutableLiveData<Boolean>()
    var enabledReset: LiveData<Boolean> =_enabledReset
    private val _enabledRemember= MutableLiveData<Boolean>()
    var enabledRemember: LiveData<Boolean> =_enabledRemember
    /////////////флаги
    private val _dictionary_empty= MutableLiveData<Boolean>()
    var dictionary_empty: LiveData<Boolean> =_dictionary_empty
    private val _flag_next= MutableLiveData<Boolean>()
    var flag_next: LiveData<Boolean> =_flag_next
    // флаг окончания показа карточек(чтобы не показывать последнюю)
    private val _flag_end= MutableLiveData<Boolean>()
    var flag_end: LiveData<Boolean> =_flag_end
    private val _flag_timer= MutableLiveData<Boolean>()///таймер работает
    val flag_timer: LiveData<Boolean> =_flag_timer
    /////время выбираемое в spinner
    private var _time = MutableLiveData<Long>()
    val time : LiveData<Long> = _time
       //////таймер//////////////
    //время через которое выкл таймер= countDownInterval*countCard
    //пересчитываем каждый раз при изменении  countDownInterval или countCard
    private val _millisInFuture= MutableLiveData<Long>()
    var millisInFuture: LiveData<Long> =_millisInFuture
    private val _countDownInterval= MutableLiveData<Long>()
    var countDownInterval: LiveData<Long> =_countDownInterval

    /////////////////выпадающий список, задающий время
    private val _timeList= MutableLiveData<Array<String>>()
    val timeList: LiveData<Array<String>> =_timeList
    /////начальная позиция списка spinner
    private val _itemSpinner= MutableLiveData<Int>()
    val itemSpinner: LiveData<Int> =_itemSpinner


    private val _visible= MutableLiveData<Boolean>()
    val visible: LiveData<Boolean> =_visible

    init { setEmpty_text()
        _size_read.value=0
        _enabledNext.value=false
        _enabledRemember.value=false
        _enabledReset.value=false
        _countCard.value=0
        _dictionary_empty.value=true
        _flag_next.value=true
        _flag_end.value=false
        /////таймер
        _millisInFuture.value=1_0000
        _countDownInterval.value=1_000
        ////
        _timeList.value = arrayOf("1 c", "3 c", "5 c", "10 c")
        _itemSpinner.value=0
        _flag_timer.value=false

        _visible.value=true

    }

    fun get_Random_id(list:List<Int>){
        val range:IntRange=list.indices// диапазон индексов списка
        val rand=range.random()//cлучайный индекс списка
        _random_id.value=list.get(rand)//случайный id Words
    }
    fun setSize_All(size:Int){
        _size_all.value=size
    }
    fun set_ruText(text:String){
        _ruText.value=text
    }
    fun set_enText(text:String){
        _enText.value=text
    }
    fun set_countCard(count:Int){
        _countCard.value=count
        _millisInFuture.value=countCard.value!!*countDownInterval.value!!
    }
    fun setSize_Read(size:Int){
        _size_read.value=size
    }
    fun setEmpty_text(){
        _ruText.value="пустой"
        _enText.value="empty"
    }
    fun setEnableRemember(enable:Boolean){
        _enabledRemember.value=enable
    }
    fun setEnableNext(enable:Boolean){
        _enabledNext.value=enable
    }
    fun setEnableReset(enable:Boolean){
        _enabledReset.value=enable
    }

    fun setDictionaryEmpty(flag:Boolean){
        _dictionary_empty.value=flag
    }
    fun setFlagNext(flag:Boolean){
        _flag_next.value=flag
    }
    fun setFlagEnd(flag:Boolean){
        _flag_end.value=flag
    }
    fun reset(){
        super.updateAll_Read()
        setFlagNext(true)
    }
    fun remember(){
        super.updateRemember(random_id.value!!)
        super.updateRead(random_id.value!!)
        setFlagNext(true)
    }
    fun next(){
        super.updateRead(random_id.value!!)
        setFlagNext(true)
    }
    fun setCountDownInterval(time:Long){
        _countDownInterval.value=time
        _millisInFuture.value=countCard.value!!*countDownInterval.value!!
    }
    fun setItemSpinner(item:Int){
        _itemSpinner.value=item
    }
    fun setFlagTimer(flag:Boolean){
        _flag_timer.value=flag
    }

    inner class MyTimer(millisInFuture: Long, countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval){
        var count=0
        var init=0

        override fun onTick(millisUntilFinished: Long) {
            count = (millisUntilFinished / 10000).toInt()
            next()
            Log.i("LOG", "TIMer")
        }

        override fun onFinish() {
            super.cancel()
        }
    }
    fun createTimer():MyTimer{
        return MyTimer(millisInFuture.value!!, countDownInterval.value!!)
    }

    /*@BindingAdapter("android:visibility")
    fun visibility (view : View, visible : Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
    */




}

