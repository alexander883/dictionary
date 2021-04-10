package com.example.youwords.data

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import kotlinx.coroutines.Dispatchers
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

    val allWords: LiveData<List<Int>> = repository.allId.asLiveData()


    ///////////////////////////////////////////
    private var _list_id = MutableLiveData<List<Int>>()//cписок id
    val list_id: LiveData<List<Int>> =_list_id
    private val _random=MutableLiveData<Int>()
    val random: LiveData<Int> =_random

    fun get_Random_id(list:List<Int>){
    _list_id.value=list.toMutableList()
    val range:IntRange=_list_id.value?.indices ?: 1..1// диапазон id
    _random.value=range.random()
   (_list_id.value as MutableList<Int>).removeAt( _random.value  ?:0)
    }
    val ranWord:LiveData<Words> =  repository.ranWord(_random.value ?: 1).asLiveData()
}



