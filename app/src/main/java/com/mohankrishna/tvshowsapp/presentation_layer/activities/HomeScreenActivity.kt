package com.mohankrishna.tvshowsapp.presentation_layer.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.mohankrishna.tvshowsapp.R
import com.mohankrishna.tvshowsapp.databinding.ActivityHomeScreenBinding

import com.mohankrishna.tvshowsapp.presentation_layer.fragments.HomeScreenFragment
import com.mohankrishna.tvshowsapp.presentation_layer.fragments.SearchScreenFragment
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.koin.android.ext.android.inject

class HomeScreenActivity : AppCompatActivity() {
    lateinit var homeScreenBinding: ActivityHomeScreenBinding
    val homeScreenViewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)

        if (supportFragmentManager.findFragmentByTag("TrendingShowsFrag") == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.trendingFragment, HomeScreenFragment(), "TrendingShowsFrag")
                .commit()
        }

        if (supportFragmentManager.findFragmentByTag("SearchFragment") == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.searchFragment, SearchScreenFragment(), "SearchFragment")
                .commit()
        }



        //Don't Delete Commented Data

        /*  homeScreenAdapter = HomePaginationAdapter()
          homeScreenBinding.myRecyclerview.adapter = homeScreenAdapter
          homeScreenBinding.myRecyclerview.layoutManager = GridLayoutManager(this, 2)*/
        /*CoroutineScope(Dispatchers.IO).launch {
            getTrendingTvShows()
        }*/
    }

    //Don't Delete Commented Data
/*
    private suspend fun getTrendingTvShows() {

        //without pagination don't delete this code
        /*homeScreenViewModel.getTrendingTvShowsData().observe(this) {
            it.observe(this) { responseData ->
                setDataToViews(responseData)
            }
        }*/

    }
*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeScreenViewModel.setSearchValue(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeScreenBinding.trendingFragment.visibility= View.GONE
                homeScreenBinding.searchFragment.visibility=View.VISIBLE
                homeScreenViewModel.searchKeyValue.value=newText
                return true
            }
        })
        return true
    }


    //Commented Data Don't Delete

/*
     suspend fun getTvShowsByWeek() {
        homeScreenViewModel.trendigDataForWeek.collect{
            withContext(Dispatchers.Main){
                homeScreenAdapter.submitData(PagingData.empty())
                homeScreenAdapter.submitData(it)

            }
        }


       //Don't delete this code
        */
/*homeScreenViewModel.getWeekTvShowsData().observe(this) {
            it.observe(this) { responseData ->
                setDataToViews(responseData)
            }
        }*//*

    }
*/
/*
    suspend fun getSearchViewData(newText: String) {
        homeScreenViewModel.setSearchKey(newText)
        homeScreenViewModel.searchDataByName.collect{
            withContext(Dispatchers.Main){
                homeScreenAdapter.submitData(PagingData.empty())
                homeScreenBinding.progressbarLayout.visibility=View.GONE
                homeScreenAdapter.submitData(it)
            }
        }

        //Don't delete this data
        */
/*homeScreenViewModel.searchTvShows(newText).observe(this) {
            it.observe(this) { responseData ->
                setDataToViews(responseData)
            }
        }*//*

    }
*/
/*
    private fun setDataToViews(it: ResponseListerner?) {
        when (it) {
            is ResponseListerner.Loading -> {
                homeScreenBinding.progressbarLayout.visibility = View.VISIBLE
            }
            is ResponseListerner.Success -> {
                //it.data?.let { it1 -> homeScreenAdapter.updateProductsList(it1) }
                homeScreenBinding.progressbarLayout.visibility = View.GONE
            }
            is ResponseListerner.Failure -> {
                homeScreenBinding.progressbarLayout.visibility = View.GONE
            }
            else -> {
                homeScreenBinding.progressbarLayout.visibility = View.GONE
            }
        }
    }
*/


}
