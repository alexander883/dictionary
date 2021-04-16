package com.example.youwords.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.data.WordViewModel

class StartViewModelFactory(
      val wordModel: WordViewModel) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return StartViewModel(wordModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}