package com.example.youwords.redact_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.R
import com.example.youwords.databinding.ActivityRedactBinding

class RedactActivity : AppCompatActivity() {
    private lateinit  var redactviewmodel: RedactActivityViewModel
    private var binding: ActivityRedactBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_redact)
        redactviewmodel = ViewModelProvider(this).get( RedactActivityViewModel::class.java)
        binding?.apply {
               redactViewModel= redactviewmodel
               activityRedact=this@RedactActivity
               lifecycleOwner= LifecycleOwner { lifecycle }
        }
        val id=intent.getIntExtra("id", 0)// получаем Id редактируемого слова
            redactviewmodel.word_by_id(id).observe(this, Observer {
            redactviewmodel.setWord(it)
        })
    }

    fun redactWord() {
        val en_word = binding?.enEditText?.text.toString().trim()
        val ru_word = binding?.rusEditText?.text.toString().trim()
        if (en_word.isEmpty() or ru_word.isEmpty()) {     // проверяем заполненность
            Toast.makeText(this, R.string.empty_pole, Toast.LENGTH_LONG).show()
        }
        else{
            redactviewmodel.redact_Word(en_word, ru_word)
            this.finish()
        }
    }
}





