package com.myapp.simpledictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Words::class], version = 1, exportSchema = false)
abstract class WordDatabase :RoomDatabase(){
abstract  fun wordDao():WordDAO

    companion object{
        @Volatile
        private var  INSTANCE:WordDatabase?=null

        fun getDatabase(context: Context):WordDatabase{
            val tempInstance=INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val  instance=Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "english"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }}
