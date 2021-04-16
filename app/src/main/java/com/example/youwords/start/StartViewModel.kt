package com.example.youwords.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.youwords.data.WordDatabase
import com.example.youwords.data.WordRepository
import com.example.youwords.data.WordViewModel


class StartViewModel(wordModel: WordViewModel) : ViewModel()   {

    val all_id: LiveData<List<Int>> =wordModel.repository.allId.asLiveData()
}