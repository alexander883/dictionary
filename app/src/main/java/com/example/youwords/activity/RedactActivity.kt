package com.example.youwords.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
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
import com.example.youwords.allwords.AllWordsFragment
import com.example.youwords.allwords.OnDataPass
import com.example.youwords.allwords.OnFragmentInteractionListener
import com.example.youwords.data.WordViewModel
import com.example.youwords.words.WordsReadViewModel


class RedactActivity : AppCompatActivity() {
    private lateinit  var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redact)
        Log.i("LOG","ВЫзов " )

        wordViewModel = ViewModelProvider(this).get( WordsReadViewModel::class.java)

        val id=intent.getIntExtra("id", 0)// получаем Id редактируемого слова


       Toast.makeText(this,"$id", Toast.LENGTH_SHORT).show()
    }




}





