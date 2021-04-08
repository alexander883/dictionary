package com.example.youwords.data

class WordRepository(private  val wordDao:WordDAO) {
    suspend fun addWord(word:Words){
        wordDao.addWord(word)
    }
}