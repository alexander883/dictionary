package com.example.youwords.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private  val wordDao:WordDAO) {
    suspend fun addWord(word:Words){
        wordDao.addWord(word)
    }
    val allId_read_not_remember: Flow<List<Int>> =wordDao.getId_read_notremember()

    val ranWord:(Int)-> Flow<Words> = { i:Int-> wordDao.selectWord(i) }

    suspend fun updateRead(random_id:Int){
        wordDao.updateRead(random_id)
    }
    suspend fun updateAll_Read(){
        wordDao.updateAll_Read()
    }
    val searchWord:(String)-> Flow<List<Words>> = { i:String-> wordDao.searchWord(i) }

    val allWords:Flow<List<Words>> =  wordDao.allWords()

    suspend fun updateRemember(id:Int){
        wordDao.updateRemember(id)
    }
    suspend fun updateAll_Remember(){
        wordDao.updateAll_Remember()
    }
    suspend fun deleteWord(word: Words){
        wordDao.deleteWord(word)
    }
   }

