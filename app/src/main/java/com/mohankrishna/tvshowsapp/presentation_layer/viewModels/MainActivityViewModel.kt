package com.mohankrishna.tvshowsapp.presentation_layer.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import com.mohankrishna.tvshowsapp.domain.repository.online.PagingRepository.PaginationDataSource
import kotlinx.coroutines.flow.flow


class MainActivityViewModel(var commonRepositoryModel: CommonRepositoryModel):ViewModel() {

    var searchKeyValue=MutableLiveData<String>()

    val trendingData = Pager(PagingConfig(1)) {
        PaginationDataSource(commonRepositoryModel,"NA","allTrending")
    }.flow

    val trendigDataForWeek = Pager(PagingConfig(1)) {
        PaginationDataSource(commonRepositoryModel,"NA","weekData")
    }.flow


    fun setSearchValue(newText:String){
        searchKeyValue.value=newText
    }



    fun searchTvShows(searchKey:String) = liveData{
        var responseListerner = commonRepositoryModel
            .getTvShowsByName(searchKey)
        emit(responseListerner)
    }


    //Without Pagination Don't Delete
    /*fun getTrendingTvShowsData()= liveData{
        var responseListerner=
            commonRepositoryModel.getTrendingTvShowsData(BuildConfig.API_KEY)
        emit(responseListerner)
    }*/
    /*fun getWeekTvShowsData()= liveData{
            var responseListerner = commonRepositoryModel
                .getWeekTvShowsData(BuildConfig.API_KEY)
            emit(responseListerner)
    }*/
}