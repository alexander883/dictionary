package com.example.youwords.start

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.youwords.data.WordDatabase
import com.example.youwords.data.WordRepository
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
    /////////////
    private val _dictionary_empty= MutableLiveData<Boolean>()
    var dictionary_empty: LiveData<Boolean> =_dictionary_empty

    private val _flag_next= MutableLiveData<Boolean>()
    var flag_next: LiveData<Boolean> =_flag_next

    // флаг окончания показа карточек(чтобы не показывать последнюю)
    private val _flag_end= MutableLiveData<Boolean>()
    var flag_end: LiveData<Boolean> =_flag_end

    fun get_Random_id(list:List<Int>){
        val range:IntRange=list.indices// диапазон индексов списка
        val rand=range.random()//cлучайный индекс списка
        _random_id.value=list.get(rand)//случайный id Words
        // (_mutablelist_id.value as MutableList<Int>).removeAt(rand)// удаляем из списка позицию с случайн id
        // return random.value!!
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
    }
    fun setSize_Read(size:Int){
        _size_read.value=size
    }
    fun setEmpty_text(){
        _ruText.value="пустой"
        _enText.value="empty"
    }
    init { setEmpty_text()
        setEmpty_text()
        _size_read.value=0
        _enabledNext.value=false
        _enabledRemember.value=false
        _enabledReset.value=false
        _countCard.value=0
        _dictionary_empty.value=true
        _flag_next.value=true
        _flag_end.value=false
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

}