package com.example.youwords.activity

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.R
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.ActivityRedactBinding
import com.example.youwords.start.StartViewModel

class RedactActivity : AppCompatActivity() {
    private lateinit  var wordviewmodel: WordViewModel
    private var binding: ActivityRedactBinding? = null
    private lateinit  var word: Words

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_redact)
        wordviewmodel = ViewModelProvider(this).get( StartViewModel::class.java)
        binding?.apply {
               viewModel=wordviewmodel
               activityRedact=this@RedactActivity
        }
        val id=intent.getIntExtra("id", 0)// получаем Id редактируемого слова
            wordviewmodel.word_by_id(id).observe(this, Observer {
            binding?.enEditText?.text=it.enWord.toEditable()
            binding?.rusEditText?.text=it.ruWord.toEditable()
            word=it
        })
    }

    fun redactWord() {// проверяем заполненность
        val en_word = binding?.enEditText?.text.toString().trim()
        val ru_word = binding?.rusEditText?.text.toString().trim()
        if (en_word.isEmpty() or ru_word.isEmpty()) {
            Toast.makeText(this, R.string.empty_pole, Toast.LENGTH_LONG).show()
        }
        else{
            word = Words(word.id, en_word, ru_word, word.read, word.remember)
            wordviewmodel.updateRedact(word)
            this.finish()
        }
    }
//расширение для установки текста в EditText
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}





