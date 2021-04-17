package com.example.youwords.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words

class SearchViewModel(application: Application) : WordViewModel(application)  {

 //   private val _search_list_words= MutableLiveData<List<Words>>()
            //  val search_list_words : LiveData<List<Words>> = _search_list_words
    fun getSearchWords(list:List<Words>){
       // _search_list_words.value=list
    }

}