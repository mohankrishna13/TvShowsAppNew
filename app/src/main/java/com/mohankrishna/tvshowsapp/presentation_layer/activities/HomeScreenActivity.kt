package com.mohankrishna.tvshowsapp.presentation_layer.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mohankrishna.tvshowsapp.R
import com.mohankrishna.tvshowsapp.databinding.ActivityHomeScreenBinding
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.presentation_layer.adapters.HomeScreenAdapter
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.MainActivityViewModel
import org.koin.android.ext.android.inject

class HomeScreenActivity : AppCompatActivity() {
    lateinit var homeScreenBinding: ActivityHomeScreenBinding
    lateinit var homeScreenAdapter: HomeScreenAdapter
    val homeScreenViewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)

        homeScreenAdapter = HomeScreenAdapter(arrayListOf())
        homeScreenBinding.myRecyclerview.adapter = homeScreenAdapter
        homeScreenBinding.myRecyclerview.layoutManager = GridLayoutManager(this, 2)

        homeScreenViewModel.getTrendingTvShowsData()
        homeScreenViewModel.data.observeForever( Observer {
            when (it) {
                is ResponseListerner.Loading -> {
                    homeScreenBinding.progressbarLayout.visibility = View.VISIBLE
                }
                is ResponseListerner.Success -> {
                    it.data?.let { it1 -> homeScreenAdapter.updateProductsList(it1) }
                    homeScreenBinding.progressbarLayout.visibility = View.GONE
                }
                is ResponseListerner.Failure -> {
                    homeScreenBinding.progressbarLayout.visibility = View.GONE
                }

                else -> {
                    homeScreenBinding.progressbarLayout.visibility = View.GONE
                }
            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty() || newText.isBlank()){
                    homeScreenViewModel.getWeekTvShowsData()

                }else{
                    homeScreenViewModel.searchTvShows(newText)
                }
                return true
            }
        })
        return true
    }

}
