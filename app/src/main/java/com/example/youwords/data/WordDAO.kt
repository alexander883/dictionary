package com.example.youwords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDAO{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Words)

 //   @Query("SELECT *  FROM english WHERE id = :random_id")
 //   suspend fun selectWord(random_id:Int):Words
}