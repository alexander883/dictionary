package com.example.youwords.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private  val wordDao:WordDAO) {
    suspend fun addWord(word:Words){
        wordDao.addWord(word)
    }
    val allId: Flow<List<Int>> =wordDao.getId()



}