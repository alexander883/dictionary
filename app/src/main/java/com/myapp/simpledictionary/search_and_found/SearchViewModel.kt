package com.myapp.simpledictionary.search_and_found

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myapp.simpledictionary.data.WordViewModel
import com.myapp.simpledictionary.data.Words

class SearchViewModel(application: Application) : WordViewModel(application)  {
    private val _searchWords=MutableLiveData<List<Words>>()
    val searchWords: LiveData<List<Words>> =_searchWords
    //кол-во найденных позиций
    private var _foundSize = MutableLiveData<Int>()
    val foundSize : LiveData<Int> = _foundSize

    fun getSize(list:List<Words>){
        _searchWords.value=list
        _foundSize.value=list.size
    }
}