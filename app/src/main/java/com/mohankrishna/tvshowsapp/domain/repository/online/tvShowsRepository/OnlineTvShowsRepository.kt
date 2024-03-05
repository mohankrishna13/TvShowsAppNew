package com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository

import androidx.lifecycle.MutableLiveData
import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.domain.model.SearchResponseListerner
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface OnlineTvShowsRepository {
    fun getTrendingTvShows(apiKey: String): MutableLiveData<ResponseListerner>
    suspend fun getWeekTrendingShows(apiKey: String):MutableLiveData<ResponseListerner>
    suspend fun getSimilarTvShows(id: Int, apiKey: String, currentPage: Int):Response<TvShowsDataModel>
    fun getTvShowsByName(apiKey: String, searchKey: String):MutableLiveData<ResponseListerner>
}