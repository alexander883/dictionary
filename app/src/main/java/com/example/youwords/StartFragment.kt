package com.example.youwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentStartBinding
import android.widget.Toast

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private lateinit var wordviewmodel: WordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wordviewmodel = ViewModelProvider(this).get(WordViewModel::class.java)
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            wordViewModel=wordviewmodel
            startFragment=this@StartFragment
        }
    }
        fun addWord(){
        findNavController().navigate(R.id.action_startFragment_to_addWordFragment)
        }
        fun goSearchFragment(){
        findNavController().navigate(R.id.action_startFragment_to_searchFragment)
        }

   fun go2_to_wordsFragment(){ // если БД пустая выводим сообщение, на другой фрагмент не идем
                               // иного способа обработать пустой List<Int>! не нашел
        wordviewmodel.all_id.observe(viewLifecycleOwner, Observer {
            val h=try { it.get(0).toString()
                findNavController().navigate(R.id.action_startFragment_to_wordsFragment)
            }
            catch (e: Exception)
            { Toast.makeText(requireContext(), "Словарь пуст!", Toast.LENGTH_LONG).show()}
        })
    }

}