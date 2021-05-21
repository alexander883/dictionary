package com.myapp.simpledictionary.allwords


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.myapp.simpledictionary.R
import com.myapp.simpledictionary.adapter_all_words.AllWordsAdapter
import com.myapp.simpledictionary.data.Words
import com.myapp.simpledictionary.databinding.FragmentAllWordsBinding


class AllWordsFragment : Fragment(), AllWordsAdapter.OnItemClickListener {
    private var binding: FragmentAllWordsBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    private val adapter = AllWordsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allwordsviewmodel = ViewModelProvider(requireActivity()).get(AllWordsViewModel::class.java)
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
        //получаем количество карточек
        allwordsviewmodel.wordCard.observe(viewLifecycleOwner, Observer {
            it?.let { allwordsviewmodel.setCountCard(it.size) }
        })
          }

    override fun onItemClick(position: Int) {
        val clickedItem =adapter.data[position]
        alertDialog(clickedItem)
    }

    ///alert dialog
    private fun alertDialog(word:Words){
        lateinit var strItems1: String
        if (word.remember)
        {  strItems1=getString(R.string.add_card)}
        else{
             strItems1=getString(R.string.del_card)
        }
         val items = arrayOf(getString(R.string.del_from_dictionary),strItems1 , getString(R.string.redact))

         val builder = AlertDialog.Builder(requireContext())
         with(builder)
         {
             setTitle(word.enWord)
             setItems(items) { dialog, which ->
                 when(which){
                     0-> deletWord(word)
                     1->{if (word.remember)
                     {  setNotRemember(word)
                           }
                     else{
                        setRemember(word)
                     }}
                     2->{/////передаем в redactActivity id редактируемого слова
                         val bundle = Bundle()
                         bundle.putInt("id", word.id)
                         findNavController().navigate(R.id.action_allWordsFragment_to_redactActivity, bundle)
                     }
                 }
             }
             show()
         }
     }
      private fun deletWord(word:Words){
                 allwordsviewmodel.deleteWord(word)
                 val en=word.enWord
                 Toast.makeText(requireContext(), "$en удален", Toast.LENGTH_SHORT).show()
      }
    private fun setRemember(word: Words){
        allwordsviewmodel.updateRemember(word.id)
    }
    private fun setNotRemember(word: Words){
        allwordsviewmodel.updateNotRemember(word.id)
    }
}

