package com.example.youwords.words

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel

class WordsReadViewModel(application: Application) : WordViewModel(application)  {

   // private val _list_id= MutableLiveData<List<Int>>()// список id получаемый из БД и неизменяемый

//cписок id
  //  private var _mutablelist_id = MutableLiveData<List<Int>>()
   // val mutablelist_list_id: LiveData<List<Int>> =_mutablelist_id


    private val _random_id=MutableLiveData<Int>()//случайный id
    val random_id: LiveData<Int> =_random_id

            // количество всех слов в словаре
    private var _size_all = MutableLiveData<Int>()
    val size_all : LiveData<Int> = _size_all
    ///
    private var _size_rem = MutableLiveData<Int>()
    val size_rem : LiveData<Int> = _size_rem


 /*   fun getList_id(list:List<Int>){ //
      //  _list_id.value=list
        _mutablelist_id.value=list.toMutableList()
    }*/
    fun get_Random_id(list:List<Int>){
        val range:IntRange=list.indices// диапазон индексов списка
        val rand=range.random()//cлучайный индекс списка
        _random_id.value=list.get(rand)//случайный id Words
       // (_mutablelist_id.value as MutableList<Int>).removeAt(rand)// удаляем из списка позицию с случайн id
       // return random.value!!
    }
  //  fun getSizeList():Int?{
  //      return  mutablelist_list_id.value?.size
  //  }

    fun setSize_All(size:Int){
        _size_all.value=size
    }
    fun setSize_Rem(size:Int){
        _size_rem.value=size
    }

}