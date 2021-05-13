package com.example.youwords.search_and_found

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words

class SearchViewModel(application: Application) : WordViewModel(application)  {
    private val _search_words=MutableLiveData<List<Words>>()
    val search_words: LiveData<List<Words>> =_search_words
    //кол-во найденных позиций
    private var _found_size = MutableLiveData<Int>()
    val found_size : LiveData<Int> = _found_size

    fun getSize(list:List<Words>){
        _search_words.value=list
        _found_size.value=list.size
    }
}