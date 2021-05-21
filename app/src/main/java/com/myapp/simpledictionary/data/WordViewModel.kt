package com.myapp.simpledictionary.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class WordViewModel(application:Application):AndroidViewModel(application) {
    private val repository: WordRepository
    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
    }

    val idRemainWord: LiveData<List<Int>> = repository.idRemainWord.asLiveData()

    fun addWord(word: Words) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }

    val searchWord:(String)-> LiveData<List<Words>>  = { i:String-> repository.searchWord(i).asLiveData() }

    val allWords:LiveData<List<Words>> = repository.allWords.asLiveData()

    fun updateRead(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRead(id)
        }
    }
     //получаем cлово по id
    val wordById:(Int?)->LiveData<Words> ={ id->repository.wordById(id).asLiveData()}

    fun updateAllRead() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllRead()
        }
    }

    fun updateRemember(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRemember(id)
        }
    }
    fun updateNotRemember(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotRemember(id)
        }
    }
    fun updateAllRemember() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllRemember()
        }
    }
    fun updateAllNotRemember() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllNotRemember()
        }
    }
    fun deleteWord(word: Words){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(word)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
    fun updateRedact(word:Words) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRedact(word)
        }
    }
    val wordCard: LiveData<List<Words>> = repository.wordCard.asLiveData()
}



