package com.example.youwords.allwords

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words


class  AllWordsViewModel(application: Application) : WordViewModel(application)  {
    private var _size = MutableLiveData<Int>()
    val size : LiveData<Int> = _size

    private val _countCard=MutableLiveData<Int>()
    var countCard: LiveData<Int> =_countCard

    fun getSize(size:Int){
        _size.value=size
    }

    fun set_countCard(count:Int){
        _countCard.value=count
    }
}