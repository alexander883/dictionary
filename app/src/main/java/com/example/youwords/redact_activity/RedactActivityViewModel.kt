package com.example.youwords.redact_activity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words

class  RedactActivityViewModel(application: Application) : WordViewModel(application)  {
    private var _enText = MutableLiveData<String>()
    val enText : LiveData<String> =_enText
    private var _ruText = MutableLiveData<String>()
    val ruText : LiveData<String> =_ruText

    private var _word = MutableLiveData<Words>()
    private val word : LiveData<Words> =_word

    fun redactWord(enText:String, ruText:String){
         val w=Words(
             id=word.value!!.id, ruWord = ruText, enWord = enText, read = word.value!!.read, remember = word.value!!.remember)
        super.updateRedact(w)
    }

    fun setWord(word: Words){
        _word.value=word
        _enText.value=word.enWord
        _ruText.value=word.ruWord
    }
}