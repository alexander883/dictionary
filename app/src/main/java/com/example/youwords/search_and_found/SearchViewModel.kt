package com.example.youwords.search_and_found

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words

class SearchViewModel(application: Application) : WordViewModel(application)  {
    private val _search_words=MutableLiveData<List<Words>>()
    val search_words: LiveData<List<Words>> =_search_words

    fun getSearchWords(list:List<Words>){
        _search_words.value=list
    }
}