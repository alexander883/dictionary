package com.example.youwords.data

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
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


    val allWords: LiveData<List<Int>> = repository.allId.asLiveData()
fun  g(c:Context){
    Toast.makeText(c,  "it[0].toString()", Toast.LENGTH_LONG).show()
}

    }



