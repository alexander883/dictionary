package com.example.youwords.allwords

import android.content.DialogInterface
import android.os.Bundle
import android.provider.UserDictionary
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.adapter_all_words.AllWordsAdapter
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentAllWordsBinding




class AllWordsFragment : Fragment(), AllWordsAdapter.OnItemClickListener {
    private var binding: FragmentAllWordsBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    val adapter = AllWordsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allwordsviewmodel = ViewModelProvider(this).get(AllWordsViewModel::class.java)
        val fragmentBinding = FragmentAllWordsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            allWordsViewModel=allwordsviewmodel
            allwordsFragment=this@AllWordsFragment
            allWordsList.adapter=adapter
        }


         allwordsviewmodel.allWords.observe(viewLifecycleOwner, Observer {
              allwordsviewmodel.getSize(it.size)
              adapter.data=it
        })


    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem =adapter.data[position]
        alertDialog(clickedItem)
    }



    ///alert dialog
   fun alertDialog(word:Words){
        lateinit var str_items_1: String
        if (word.remember==true)
        {  str_items_1="Добавить в карточки"}
        else{
             str_items_1="Удалить из карточек"
        }
         val items = arrayOf("Удалить из словаря",str_items_1 , "Редактировать")

         val builder = AlertDialog.Builder(requireContext())
         with(builder)
         {
             setTitle("List of Items")
             setItems(items) { dialog, which ->
                 when(which){
                     0-> deletWord(word)
                     1->{if (word.remember==true)
                     {  setNotRemember(word)}
                     else{
                        setRemember(word)
                     }}
                 }


                 Toast.makeText(requireContext(), items[which] + " is clicked", Toast.LENGTH_SHORT).show()
             }

           // setPositiveButton("OK",alertDialog(list))
             show()
         }
     }
    private fun deletWord(list:Words){
                 allwordsviewmodel.deleteWord(list)
                 val en=list.enWord
                 Toast.makeText(requireContext(), "$en удален", Toast.LENGTH_SHORT).show()
         }
    private fun setRemember(word: Words){
        allwordsviewmodel.updateRemember(word.id)
    }
    private fun setNotRemember(word: Words){
        allwordsviewmodel.updateNotRemember(word.id)
    }

}
