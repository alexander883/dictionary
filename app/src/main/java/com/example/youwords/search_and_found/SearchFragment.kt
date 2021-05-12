package com.example.youwords.search_and_found

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.activity.ActivityInterractor

import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentSearchBinding




class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var searchviewmodel:SearchViewModel
    var transfer:ActivityInterractor?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
           context as ActivityInterractor
            transfer=context
    Log.i("LOG", "try")
         }
catch (e:Exception)
{ Log.i("LOG", "catch")
    Toast.makeText(requireContext(), "Не србаоало", Toast.LENGTH_SHORT).show()}
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

    } fun searhWord(){
      //  binding?.searchText?.text ?: Toast.makeText(requireContext(), "Введите значение", Toast.LENGTH_LONG).show()
       val search=binding?.searchText?.text.toString()

        searchviewmodel.searchWord(search).observe(viewLifecycleOwner, Observer {
            try { it[0]/// если it[0] не существует=> catch
                searchviewmodel.getSearchWords(it)
                findNavController().navigate(R.id.action_searchFragment_to_foundFragment)
                transfer?.transferOnSearchFragment()
                hideKeyboardFrom(requireContext(),view)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Не найдено ", Toast.LENGTH_LONG).show()
                Log.i("LOG", "Не найдено ")
            }
            }        )
        }
    private fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    }

