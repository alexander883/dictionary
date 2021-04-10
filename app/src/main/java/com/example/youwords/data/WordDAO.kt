package com.example.youwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Words)

    @Query("SELECT *  FROM english WHERE id = :random_id")
     fun selectWord(random_id: Int): Flow<Words>

    @Query("SELECT id FROM english ") // получаем список id
     fun getId(): Flow<List<Int>>
}