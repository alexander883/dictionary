package com.example.youwords.data

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WordViewModel(application:Application):AndroidViewModel(application) {
    private val repository: WordRepository
    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
    }

    fun addWord(word: Words) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }

    val all_id: LiveData<List<Int>> = repository.allId.asLiveData()
     //получаем cлово по id
    val randWord:(Int)->LiveData<Words> ={id->repository.ranWord(id).asLiveData()}
    ///////////////////////////////////////////
    private val _list_id=MutableLiveData<List<Int>>()// список id получаемый из БД и неизменяемый
    private var _mutablelist_id = MutableLiveData<List<Int>>()//cписок id который изменяем
    val mutablelist_list_id: LiveData<List<Int>> =_mutablelist_id

    private val _random=MutableLiveData<Int>()//случайный id
    val random: LiveData<Int> =_random

    private val _search_list_words=MutableLiveData<List<Words>>()
    val searh_list_words :LiveData<List<Words>> = _search_list_words

    fun getList_id(list:List<Int>){
        _list_id.value=list
        _mutablelist_id.value=list.toMutableList()
    }

     fun get_Random_id():Int{
     val range:IntRange=mutablelist_list_id.value?.indices ?: 0..0// диапазон индексов списка
     val rand=range.random()//cлучайный индекс списка
     _random.value= mutablelist_list_id.value?.get(rand)//случайный id Words
     (_mutablelist_id.value as MutableList<Int>).removeAt(rand)// удаляем из списка позицию с случайн id
         return random.value ?: 1
    }
    fun getSizeList():Int?{
        return  mutablelist_list_id.value?.size
    }
    fun getSearchWords(list:List<Words>){
        _search_list_words.value=list
    }


    ////////////

    fun updateRead() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRead(random.value ?: 1)
        }
    }
    fun updateAll_Read() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAll_Read()
        }
    }

    val searchWord:(String)-> LiveData<List<Words>>  = { i:String-> repository.searchWord(i).asLiveData() }

}



