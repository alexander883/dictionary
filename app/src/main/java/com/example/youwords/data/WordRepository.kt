package com.example.youwords.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private  val wordDao:WordDAO) {
    suspend fun addWord(word:Words){
        wordDao.addWord(word)
    }
    val allId: Flow<List<Int>> =wordDao.getId()

    val ranWord:(Int)-> Flow<Words> = { i:Int-> wordDao.selectWord(i) }

    suspend fun updateRead(random_id:Int){
        wordDao.updateRead(random_id)
    }
    suspend fun updateAll_Read(){
        wordDao.updateAll_Read()
    }
    val searchWord:(String)-> Flow<List<Words>> = { i:String-> wordDao.searchWord(i) }

   }

