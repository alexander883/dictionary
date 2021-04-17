package com.example.youwords.words

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.youwords.databinding.FragmentWordsreadBinding


class WordsReadFragment : Fragment() {

    private var binding: FragmentWordsreadBinding?=null
    private lateinit var wordsreadviewmodel: WordsReadViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        wordsreadviewmodel = ViewModelProvider(this).get( WordsReadViewModel::class.java)
        val fragmentBinding = FragmentWordsreadBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
g()
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordsViewModel = wordsreadviewmodel
            wordsreadFragment = this@WordsReadFragment

        }


        binding?.buttonReset?.setOnClickListener {
            wordsreadviewmodel.updateAll_Read()
            binding?.next?.setEnabled(true)
        }

        binding?.next?.setOnClickListener {
           // g()
            //  binding?.next?.setEnabled(false)
            changeText()
            changeCount()
            update()
            if (wordsreadviewmodel.getSizeList() == 0) {
                binding?.next?.setEnabled(false)
            }

        }

    }

    fun g() {
        if (wordsreadviewmodel.random.value==null) {
            Toast.makeText(requireContext(),  "Cо!!!!исок", Toast.LENGTH_LONG).show()
        wordsreadviewmodel.all_id_read.observe(viewLifecycleOwner, Observer{
            // на этот фрагмент не попадаем, если БД пустая
            // поэтому можем привести к List<Int>
            val list= it as List<Int>
            wordsreadviewmodel.getList_id(list)

            changeText()
            changeCount()
            val p=  Toast.makeText(requireContext(),  "Cоздан список8", Toast.LENGTH_LONG).show()

            if (wordsreadviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }


        })
        }
    else {    Toast.makeText(requireContext(),  "else", Toast.LENGTH_LONG).show()}}



    private fun  update(){
        wordsreadviewmodel.updateRead(wordsreadviewmodel.random.value!!)

    }
    private  fun changeText(){
        wordsreadviewmodel.randWord(getRandom_id()).observe(viewLifecycleOwner, Observer {
            binding?.enText?.text=it.enWord
            binding?.ruText?.text=it.ruWord
        })
    }
    //получаем список id
    private fun getList_ids(list:List<Int>){
        wordsreadviewmodel.getList_id(list)
    }
    private fun getRandom_id():Int{
        return wordsreadviewmodel.get_Random_id()
    }
    private fun changeCount(){
        binding?.count?.text=wordsreadviewmodel.getSizeList().toString()
        //if (wordviewmodel.getSizeList()){

    }}








