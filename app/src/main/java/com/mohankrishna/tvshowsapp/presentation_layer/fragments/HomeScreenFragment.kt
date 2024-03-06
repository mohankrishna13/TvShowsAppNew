package com.mohankrishna.tvshowsapp.presentation_layer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.mohankrishna.tvshowsapp.R
import com.mohankrishna.tvshowsapp.databinding.FragmentHomeScreenBinding
import com.mohankrishna.tvshowsapp.presentation_layer.adapters.HomePaginationAdapter
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeScreenFragment : Fragment() {

    val viewModel:MainActivityViewModel by inject()
    lateinit var binding: FragmentHomeScreenBinding
    lateinit var adapter: HomePaginationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home_screen,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= HomePaginationAdapter()
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        getTrendingData()
    }

    private fun getTrendingData() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.trendingData.collect(collector = {
                Log.e("PrintCurrentThread", "Fragment - 50 " + Thread.currentThread().name)
                binding.progressbarLayout.visibility = View.GONE
                adapter.submitData(it)
            })
        }
    }
}