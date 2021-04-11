package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.trace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentWordsBinding
import kotlinx.coroutines.flow.map


class WordsFragment : Fragment() {
    private var binding: FragmentWordsBinding?=null
    private lateinit var wordviewmodel: WordViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wordviewmodel = ViewModelProvider(this).get(WordViewModel::class.java)
        val fragmentBinding = FragmentWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordviewmodel.all_id.observe(viewLifecycleOwner, Observer{
            // на этот фрагмент не попадаем, если БД пустая
            // поэтому можем привести к List<Int>
            val list= it as List<Int>
            getList_ids(list)
            changeText()
            changeCount()
     //    Toast.makeText(requireContext(),  "$f", Toast.LENGTH_LONG).show()

            if (wordviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }


     })
        binding?.buttonReset?.setOnClickListener { wordviewmodel.updateAll_Read()
            binding?.next?.setEnabled(true) }
        binding?.next?.setOnClickListener {
            // binding?.next?.setEnabled(false)
            changeText()
            changeCount()
            update()
            if (wordviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }

        }
}      private fun  update(){
            wordviewmodel.updateRead()

    }
      private  fun changeText(){
            wordviewmodel.randWord(getRandom_id()).observe(viewLifecycleOwner, Observer {
                binding?.enText?.text=it.enWord
                binding?.ruText?.text=it.ruWord
            })
        }
     //получаем список id
    private fun getList_ids(list:List<Int>){
            wordviewmodel.getList_id(list)
    }
    private fun getRandom_id():Int{
             return wordviewmodel.get_Random_id()
    }
    private fun changeCount(){
     binding?.count?.text=wordviewmodel.getSizeList().toString()
    //if (wordviewmodel.getSizeList()){

    }}








