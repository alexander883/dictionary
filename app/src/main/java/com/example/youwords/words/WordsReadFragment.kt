package com.example.youwords.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordsViewModel=wordsreadviewmodel
           wordsreadFragment=this@WordsReadFragment
        }
        wordsreadviewmodel.all_id_read.observe(viewLifecycleOwner, Observer{
            // на этот фрагмент не попадаем, если БД пустая
            // поэтому можем привести к List<Int>
            val list= it as List<Int>
            getList_ids(list)
            changeText()
            changeCount()
            //    Toast.makeText(requireContext(),  "$f", Toast.LENGTH_LONG).show()

            if (wordsreadviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }


        })
        binding?.buttonReset?.setOnClickListener {wordsreadviewmodel.updateAll_Read()
            binding?.next?.setEnabled(true) }

        binding?.next?.setOnClickListener {
            // binding?.next?.setEnabled(false)
            changeText()
            changeCount()
            update()
            if (wordsreadviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }

        }


    }

    private fun  update(){
        wordsreadviewmodel.updateRead(5)

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








