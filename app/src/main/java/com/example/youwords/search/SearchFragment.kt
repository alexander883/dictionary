package com.example.youwords.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.RecyclerView.FoundAdapter
import com.example.youwords.data.WordViewModel
import com.example.youwords.data.Words
import com.example.youwords.databinding.FragmentSearchBinding




class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var searchviewmodel:SearchViewModel
    val adapter = FoundAdapter()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchviewmodel = ViewModelProvider(this).get(SearchViewModel::class.java)
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
       // binding?.buttonSearchWord?.setOnClickListener {
      ////  }

    } fun searhWord(){
        val search=binding?.searchText?.text.toString()
        searchviewmodel.searchWord( search).observe(viewLifecycleOwner, Observer {
            val h=try {val list= it as List<Words>
               // getSearchWords(list)

                val j=it.size.toString()
                Toast.makeText(requireContext(), j, Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_searchFragment_to_foundFragment)}

            catch (e: Exception)
            { Toast.makeText(requireContext(), "Не найдено ", Toast.LENGTH_LONG).show()}

        }

        )
    }
            private fun getSearchWords(list:List<Words>){
             searchviewmodel.getSearchWords(list)
            }
}