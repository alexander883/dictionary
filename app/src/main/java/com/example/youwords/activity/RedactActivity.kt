package com.example.youwords.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.youwords.R

import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.ActivityRedactBinding
import com.example.youwords.databinding.FragmentAddwordBinding
import com.example.youwords.words.WordsReadViewModel

import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class RedactActivity : AppCompatActivity() {
    private lateinit  var wordviewmodel: WordViewModel
    private var binding: ActivityRedactBinding? = null
    private lateinit  var word: Words

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_redact)
        wordviewmodel = ViewModelProvider(this).get( WordsReadViewModel::class.java)
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
       Toast.makeText(this,"$id", Toast.LENGTH_SHORT).show()
    }

    fun redactWord() {// проверяем заполненность
        val en_word = binding?.enEditText?.text.toString()
        val ru_word = binding?.rusEditText?.text.toString()
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





