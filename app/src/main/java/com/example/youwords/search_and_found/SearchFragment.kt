package com.example.youwords.search_and_found

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.activity.ActivityInterractor
import com.example.youwords.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var searchviewmodel:SearchViewModel
    var transfer:ActivityInterractor?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
           context as ActivityInterractor
            transfer=context
       }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchviewmodel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        val fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            searchViewModel=searchviewmodel
            searchFragment=this@SearchFragment
        }
    }
    fun searhWord(){
       val search=binding?.searchText?.text?.trim().toString()
        searchviewmodel.searchWord(search).observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                searchviewmodel.getSize(it)
                findNavController().navigate(R.id.action_searchFragment_to_foundFragment)
                transfer?.transferOnSearchFragment()
                hideKeyboardFrom(requireContext(),view)
            }
           else {
                Toast.makeText(requireContext(), "Не найдено ", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

