package com.example.youwords.search_and_found

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.adapter_found_word.FoundAdapter
import com.example.youwords.databinding.FragmentFoundBinding

class FoundFragment : Fragment() {
    private var binding: FragmentFoundBinding? = null
    private lateinit var searchviewmodel: SearchViewModel
    val adapter = FoundAdapter()

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
        adapter.data=searchviewmodel.search_words.value!!
    }
}