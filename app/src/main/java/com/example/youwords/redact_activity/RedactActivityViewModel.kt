package com.example.youwords.redact_activity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words

class  RedactActivityViewModel(application: Application) : WordViewModel(application)  {
    private var _en_text = MutableLiveData<String>()
    val en_text : LiveData<String> =_en_text
    private var _ru_text = MutableLiveData<String>()
    val ru_text : LiveData<String> =_ru_text

    private var _word = MutableLiveData<Words>()
    val word : LiveData<Words> =_word

    fun redact_Word(en_text:String, ru_text:String){
         val w=Words(
             id=word.value!!.id, ruWord = ru_text, enWord = en_text, read = word.value!!.read, remember = word.value!!.remember)
        super.updateRedact(w)
    }

    fun setWord(word: Words){
        _word.value=word
        _en_text.value=word.enWord
        _ru_text.value=word.ruWord
    }
}