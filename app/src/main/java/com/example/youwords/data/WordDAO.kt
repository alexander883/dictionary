package com.example.youwords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface WordDAO{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Words)
}