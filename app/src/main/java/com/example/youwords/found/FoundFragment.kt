package com.example.youwords.found

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.youwords.RecyclerView.FoundAdapter
import com.example.youwords.data.WordViewModel
import com.example.youwords.databinding.FragmentFoundBinding


class FoundFragment : Fragment() {
    private var binding: FragmentFoundBinding?= null
    private lateinit var foundviewmodel: FoundViewModel
    val adapter = FoundAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foundviewmodel = ViewModelProvider(this).get(FoundViewModel::class.java)
        val fragmentBinding = FragmentFoundBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding?.wordList?.adapter=adapter
        binding?.button?.setOnClickListener {  foundviewmodel.all_id.observe(viewLifecycleOwner,Observer {


            adapter.data=it

          //  val h=it.count()
           // Toast.makeText(requireContext(), "$h", Toast.LENGTH_LONG).show()
       })
        }
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            foundViewModel=foundviewmodel
            foundFragment=this@FoundFragment
            wordList.adapter=adapter
          }

     }
}