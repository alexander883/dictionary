package com.myapp.simpledictionary.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private  val wordDao:WordDAO) {
    suspend fun addWord(word:Words){
        wordDao.addWord(word)
    }
    val idRemainWord: Flow<List<Int>> =wordDao.getIdRemainWord()

    val wordById:(Int?)-> Flow<Words> = { id:Int?-> wordDao.selectWord(id) }

    suspend fun updateRead(randomId:Int){
        wordDao.updateRead(randomId)
    }
    suspend fun updateAllRead(){
        wordDao.updateAllRead()
    }
    val searchWord:(String)-> Flow<List<Words>> = { str:String-> wordDao.searchWord(str) }

    val allWords:Flow<List<Words>> =  wordDao.allWords()

    suspend fun updateRemember(id:Int){
        wordDao.updateRemember(id)
    }
    suspend fun updateNotRemember(id:Int){
        wordDao.updateNotRemember(id)
    }
    suspend fun updateAllRemember(){
        wordDao.updateAllRemember()
    }
    suspend fun updateAllNotRemember(){
        wordDao.updateAllNotRemember()
    }
    suspend fun deleteWord(word: Words){
        wordDao.deleteWord(word)
    }
    suspend fun deleteAll() {
        wordDao.deleteAll()
    }
    suspend fun updateRedact(word:Words){
        wordDao.updateRedact(word)
    }
    val wordCard: Flow<List<Words>> =wordDao.getWordCard()
   }

