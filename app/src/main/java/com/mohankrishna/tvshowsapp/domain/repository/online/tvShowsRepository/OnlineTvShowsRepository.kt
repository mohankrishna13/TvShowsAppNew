package com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository

import androidx.lifecycle.MutableLiveData
import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import retrofit2.Response

interface OnlineTvShowsRepository {
    suspend fun getTrendingTvShows(apiKey: String,pageNumber:Int): Response<TvShowsDataModel>
    suspend fun getWeekTrendingShows(apiKey: String,pageNumber: Int):Response<TvShowsDataModel>
    suspend fun getSimilarTvShows(id: Int, apiKey: String, currentPage: Int):Response<TvShowsDataModel>
    fun getTvShowsByName(apiKey: String, searchKey: String):MutableLiveData<ResponseListerner>
}