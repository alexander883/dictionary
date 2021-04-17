package com.example.youwords.allwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.Adapter.AllWordsAdapter
import com.example.youwords.databinding.FragmentAllWordsBinding




class AllWordsFragment : Fragment() {
    private var binding: FragmentAllWordsBinding? = null
    private lateinit var allwordsviewmodel: AllWordsViewModel
    val adapter = AllWordsAdapter()
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


            adapter.data=it

            //  val h=it.count()
            // Toast.makeText(requireContext(), "$h", Toast.LENGTH_LONG).show()
        })


    }

}