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

open class WordViewModel(application:Application):AndroidViewModel(application) {
    val repository: WordRepository

    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
    }

    val all_id: LiveData<List<Int>> = repository.allId.asLiveData()

    fun addWord(word: Words) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }

    val searchWord:(String)-> LiveData<List<Words>>  = { i:String-> repository.searchWord(i).asLiveData() }


    fun updateRead(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRead(id)
        }
    }

     //получаем cлово по id
    val randWord:(Int)->LiveData<Words> ={id->repository.ranWord(id).asLiveData()}
    ///////////////////////////////////////////











    ////////////


    fun updateAll_Read() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAll_Read()
        }
    }



}



