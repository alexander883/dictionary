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
    private var _size = MutableLiveData<Int>()
    val size : LiveData<Int> = _size

    fun getSearchWords(list:List<Words>){
        _search_words.value=list
    }
    fun getSize():String {
        _size.value=search_words.value?.size
        return size.value.toString() ?: return "0"

    }
val s: (List<Words>?)->String={it?.size.toString()}
 //   val ss=s(search_words.value)

}