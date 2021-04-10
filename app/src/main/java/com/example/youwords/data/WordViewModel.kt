package com.example.youwords.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WordViewModel(application:Application):AndroidViewModel(application) {
private val repository:WordRepository
    init {
        val wordDao=WordDatabase.getDatabase(application).wordDao()
        repository= WordRepository(wordDao)
    }
    fun  addWord(word:Words){
        viewModelScope.launch(Dispatchers.IO){
            repository.addWord(word)
        }
    }

    val allWords: LiveData<List<String>> = repository.allId.asLiveData()


    }



