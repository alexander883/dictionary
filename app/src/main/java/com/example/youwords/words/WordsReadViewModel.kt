package com.example.youwords.words

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel

class WordsReadViewModel(application: Application) : WordViewModel(application)  {

   // private val _list_id= MutableLiveData<List<Int>>()// список id получаемый из БД и неизменяемый

//cписок id
    private var _mutablelist_id = MutableLiveData<List<Int>>()
    val mutablelist_list_id: LiveData<List<Int>> =_mutablelist_id


    private val _random=MutableLiveData<Int>()//случайный id
    val random: LiveData<Int> =_random

            // количество всех слов в словаре
    private var _size = MutableLiveData<Int>()
    val size : LiveData<Int> = _size



    fun getList_id(list:List<Int>){ //
      //  _list_id.value=list
        _mutablelist_id.value=list.toMutableList()
    }
    fun get_Random_id():Int{
        val range:IntRange=mutablelist_list_id.value?.indices!!// диапазон индексов списка
        val rand=range.random()//cлучайный индекс списка
        _random.value= mutablelist_list_id.value?.get(rand)//случайный id Words
        (_mutablelist_id.value as MutableList<Int>).removeAt(rand)// удаляем из списка позицию с случайн id
        return random.value!!
    }
    fun getSizeList():Int?{
        return  mutablelist_list_id.value?.size
    }

    fun setSize_All(size:Int){
        _size.value=size
    }

}