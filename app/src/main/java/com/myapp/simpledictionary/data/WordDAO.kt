package com.myapp.simpledictionary.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Words)

    @Query("SELECT *  FROM english WHERE id = :id  ")// получаем Words по id
     fun selectWord(id: Int?): Flow<Words>

    @Query("SELECT id FROM english WHERE read=0 AND remember=0") // получаем список id
     fun getIdRemainWord(): Flow<List<Int>>

    @Query("UPDATE english SET read=1 WHERE id=:randomId") //Устанавливаем Слово прочитано
    suspend fun updateRead(randomId:Int)

    @Query("UPDATE english SET read=0") //Устанавливаем ВСе Слово не прочитано
    suspend fun updateAllRead()

    @Query("SELECT * FROM english WHERE( enWord  LIKE  '% ' || :search OR enWord LIKE  :search " +
            "OR enWord LIKE  :search || ',%' OR enWord LIKE '% ' || :search || ',%') OR" +
            " (ruWord LIKE  '% ' || :search OR ruWord LIKE  :search OR ruWord LIKE  :search || ',%' OR ruWord LIKE '% ' || :search || ',%' )")
    fun searchWord(search: String):  Flow<List<Words>>

    @Query("SELECT * FROM english ")
    fun allWords():  Flow<List<Words>>

    @Query("UPDATE english SET remember=1 WHERE id=:id") //Устанавливаем Слово запомнено
    suspend fun updateRemember(id:Int)

    @Query("UPDATE english SET remember=0 WHERE id=:id")
    suspend fun updateNotRemember(id:Int)

    @Query("UPDATE english SET remember=0") //Устанавливаем ВСе Слово не запомнено
    suspend fun updateAllRemember()

    @Query("UPDATE english SET remember=1")
    suspend fun updateAllNotRemember()

    @Delete
    suspend fun deleteWord(word: Words)

    @Query("DELETE FROM english")
    suspend fun deleteAll()

    @Update //Редактируем слово
    suspend fun updateRedact(word: Words)

    @Query("SELECT * FROM english WHERE remember=0") // card
    fun getWordCard(): Flow<List<Words>>
}