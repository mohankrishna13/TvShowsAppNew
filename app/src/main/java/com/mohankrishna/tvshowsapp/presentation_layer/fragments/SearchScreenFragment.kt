package com.mohankrishna.tvshowsapp.presentation_layer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mohankrishna.tvshowsapp.R
import com.mohankrishna.tvshowsapp.databinding.FragmentHomeScreenBinding
import com.mohankrishna.tvshowsapp.databinding.FragmentSearchScreenBinding
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.presentation_layer.adapters.HomePaginationAdapter
import com.mohankrishna.tvshowsapp.presentation_layer.adapters.HomeScreenAdapter
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class SearchScreenFragment : Fragment() {
    val myviewModel:MainActivityViewModel by inject()
    lateinit var binding: FragmentSearchScreenBinding
    lateinit var paggingAdapter: HomePaginationAdapter
    lateinit var searchNormalAdapter:HomeScreenAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search_screen,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paggingAdapter= HomePaginationAdapter()
        searchNormalAdapter= HomeScreenAdapter(arrayListOf())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        myviewModel.searchKeyValue.observe(viewLifecycleOwner, Observer {
            if(it.isBlank() || it.isEmpty()){
                fetchDataForWeek()
            }else {
                fetchDataByName(it)
            }
        })


    }

    private fun fetchDataByName(it: String?) {
        myviewModel.searchTvShows(it.toString()).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                binding.progressbarLayout.visibility=View.GONE
                setDataToViews(it)
            })
        })
    }

    private  fun fetchDataForWeek() {
       CoroutineScope(Dispatchers.IO).launch {
           myviewModel.trendigDataForWeek.collect {
                binding.progressbarLayout.visibility=View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    binding.recyclerView.adapter=paggingAdapter
                    paggingAdapter.submitData(it)

                }
            }
        }
    }

    private fun setDataToViews(it: ResponseListerner?) {
        binding.recyclerView.adapter=searchNormalAdapter
        when (it) {
            is ResponseListerner.Loading -> {
                binding.progressbarLayout.visibility = View.VISIBLE
            }
            is ResponseListerner.Success -> {
                binding.progressbarLayout.visibility = View.GONE
                searchNormalAdapter.updateProductsList(it.data)
            }
            is ResponseListerner.Failure -> {
                binding.progressbarLayout.visibility = View.GONE
            }
            else -> {
                binding.progressbarLayout.visibility = View.GONE
            }
        }
    }
}