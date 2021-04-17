package com.example.youwords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Words)

    @Query("SELECT *  FROM english WHERE id = :random_id ")// получаем Words по id
     fun selectWord(random_id: Int): Flow<Words>

    @Query("SELECT id FROM english WHERE read=0") // получаем список id
     fun getId_read(): Flow<List<Int>>

    @Query("UPDATE english SET read=1 WHERE id=:random_id") //Устанавливаем Слово прочитано
    suspend fun updateRead(random_id:Int)

    @Query("UPDATE english SET read=0") //Устанавливаем ВСе Слово не прочитано
    suspend fun updateAll_Read()

    @Query("SELECT * FROM english WHERE( enWord  LIKE :search ) OR (ruWord LIKE :search )")
    fun searchWord(search: String):  Flow<List<Words>>
}