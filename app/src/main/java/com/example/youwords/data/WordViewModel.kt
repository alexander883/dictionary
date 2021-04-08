package com.example.youwords.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
}
