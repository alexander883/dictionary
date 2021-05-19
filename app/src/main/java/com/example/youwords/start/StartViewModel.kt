package com.example.youwords.start

import android.app.Application
import android.os.CountDownTimer
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel

class StartViewModel(application:Application) : WordViewModel(application)  {
    private val _randomId= MutableLiveData<Int>()//случайный id
    val randomId: LiveData<Int> =_randomId
    // количество всех слов в словаре
    private var _sizeAll = MutableLiveData<Int>()
    val sizeAll : LiveData<Int> = _sizeAll

    private var _sizeRead = MutableLiveData<Int>()
    val sizeRead : LiveData<Int> = _sizeRead

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
    //флаги
    private val _dictionaryEmpty= MutableLiveData<Boolean>()
    var dictionaryEmpty: LiveData<Boolean> =_dictionaryEmpty
    private val _flagNext= MutableLiveData<Boolean>()
    var flagNext: LiveData<Boolean> =_flagNext
    // флаг окончания показа карточек(чтобы не показывать последнюю)
    private val _flagEnd= MutableLiveData<Boolean>()
    var flagEnd: LiveData<Boolean> =_flagEnd
    private val _flagTimer= MutableLiveData<Boolean>()///таймер работает
    val flagTimer: LiveData<Boolean> =_flagTimer
    /////время выбираемое в spinner
    private var _time = MutableLiveData<Long>()
    val time : LiveData<Long> = _time
       //////таймер//////////////
    //время через которое выкл таймер= countDownInterval*countCard
    //пересчитываем каждый раз при изменении  countDownInterval или countCard
    private val _millisInFuture= MutableLiveData<Long>()
    private var millisInFuture: LiveData<Long> =_millisInFuture

    private val _countDownInterval= MutableLiveData<Long>()
    private var countDownInterval: LiveData<Long> =_countDownInterval

    /////////////////выпадающий список, задающий время
    private val _timeList= MutableLiveData<Array<String>>()
    val timeList: LiveData<Array<String>> =_timeList
    /////начальная позиция списка spinner
    private val _itemSpinner= MutableLiveData<Int>()
    val itemSpinner: LiveData<Int> =_itemSpinner

    private val _visibilitySpinner= MutableLiveData<Boolean>()
    val visibilitySpinner: LiveData<Boolean> =_visibilitySpinner

    private val _visibilityCheckBox= MutableLiveData<Boolean>()
    val visibilityCheckBox: LiveData<Boolean> =_visibilityCheckBox

    init { setEmptyText()
        _sizeRead.value=0
        _enabledNext.value=false
        _enabledRemember.value=false
        _enabledReset.value=false
        _countCard.value=0
        _dictionaryEmpty.value=true
        _flagNext.value=true
        _flagEnd.value=false
        /////таймер
        _millisInFuture.value=1_0000
        _countDownInterval.value=1_000
        ////
        _timeList.value = arrayOf("1 c", "3 c", "5 c", "10 c")
        _itemSpinner.value=0
        _flagTimer.value=false

        _visibilitySpinner.value=false
        _visibilityCheckBox.value=false
    }

    fun getRandomId(list:List<Int>){
        val range:IntRange=list.indices// диапазон индексов списка
        val rand=range.random()//cлучайный индекс списка
        _randomId.value= list[rand]//случайный id Words
    }
    fun setSizeAll(size:Int){
        _sizeAll.value=size
    }
    fun setRuText(text:String){
        _ruText.value=text
    }
    fun setEnText(text:String){
        _enText.value=text
    }
    fun setCountCard(count:Int){
        _countCard.value=count
        setTimeOfTimer()
    }
    fun setSizeRead(size:Int){
        _sizeRead.value=size
    }
    fun setEmptyText(){
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
        _dictionaryEmpty.value=flag
    }
    fun setFlagNext(flag:Boolean){
        _flagNext.value=flag
    }
    fun setFlagEnd(flag:Boolean){
        _flagEnd.value=flag
    }
    fun reset(){
        super.updateAllRead()
        setFlagNext(true)
    }
    fun remember(){
        super.updateRemember(randomId.value!!)
        super.updateRead(randomId.value!!)
        setFlagNext(true)
    }
    fun next(){
        super.updateRead(randomId.value!!)
        setFlagNext(true)
    }
    fun setCountDownInterval(time:Long){
        _countDownInterval.value=time
        setTimeOfTimer()
    }
    fun setItemSpinner(item:Int){
        _itemSpinner.value=item
    }
    fun setVisibilitySpinner(flag: Boolean){
        _visibilitySpinner.value=flag
    }
    fun setVisibilityCheckBox(flag: Boolean){
        _visibilityCheckBox.value=flag
    }
    fun setFlagTimer(flag:Boolean){
        _flagTimer.value=flag
    }
    private fun setTimeOfTimer(){
        _millisInFuture.value=countCard.value!!*countDownInterval.value!!
    }

    inner class MyTimer(millisInFuture: Long, countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval){
        override fun onTick(millisUntilFinished: Long) {
            next()
        }
        override fun onFinish() {
            super.cancel()
        }
    }
    fun createTimer():MyTimer{
        return MyTimer(millisInFuture.value!!, countDownInterval.value!!)
    }
}
