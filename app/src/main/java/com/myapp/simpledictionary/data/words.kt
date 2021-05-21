package com.myapp.simpledictionary.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "english")
data class Words(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val enWord:String,
    val ruWord:String,
    val read:Boolean=false,
    val remember:Boolean=false)

