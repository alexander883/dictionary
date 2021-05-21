package com.myapp.simpledictionary.search_and_found

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.simpledictionary.adapter_found_word.FoundAdapter
import com.myapp.simpledictionary.databinding.FragmentFoundBinding

class FoundFragment : Fragment() {
    private var binding: FragmentFoundBinding? = null
    private lateinit var searchviewmodel: SearchViewModel
    private val adapter = FoundAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchviewmodel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        val fragmentBinding = FragmentFoundBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            searchViewModel = searchviewmodel
            foundFragment = this@FoundFragment
            foundWordsList.adapter=adapter
        }
        adapter.data=searchviewmodel.searchWords.value!!
    }
}