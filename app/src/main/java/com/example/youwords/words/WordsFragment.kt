package com.example.youwords.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentWordsBinding


class WordsFragment : Fragment() {
    private var binding: FragmentWordsBinding?=null
    private lateinit var wordsviewmodel: WordsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wordsviewmodel = ViewModelProvider(this).get(WordsViewModel::class.java)
        val fragmentBinding = FragmentWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordsViewModel=wordsviewmodel
            wordsFragment=this@WordsFragment
        }
        wordsviewmodel.all_id.observe(viewLifecycleOwner, Observer{
            // на этот фрагмент не попадаем, если БД пустая
            // поэтому можем привести к List<Int>
            val list= it as List<Int>
            getList_ids(list)
            changeText()
            changeCount()
     //    Toast.makeText(requireContext(),  "$f", Toast.LENGTH_LONG).show()

            if (wordsviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }


     })
        binding?.buttonReset?.setOnClickListener { wordsviewmodel.updateAll_Read()
            binding?.next?.setEnabled(true) }

        binding?.next?.setOnClickListener {
            // binding?.next?.setEnabled(false)
            changeText()
            changeCount()
            update()
            if (wordsviewmodel.getSizeList()==0){
                binding?.next?.setEnabled(false)
            }

        }
}      private fun  update(){
            wordsviewmodel.updateRead()

    }
      private  fun changeText(){
            wordsviewmodel.randWord(getRandom_id()).observe(viewLifecycleOwner, Observer {
                binding?.enText?.text=it.enWord
                binding?.ruText?.text=it.ruWord
            })
        }
     //получаем список id
    private fun getList_ids(list:List<Int>){
            wordsviewmodel.getList_id(list)
    }
    private fun getRandom_id():Int{
             return wordsviewmodel.get_Random_id()
    }
    private fun changeCount(){
     binding?.count?.text=wordsviewmodel.getSizeList().toString()
    //if (wordviewmodel.getSizeList()){

    }}








