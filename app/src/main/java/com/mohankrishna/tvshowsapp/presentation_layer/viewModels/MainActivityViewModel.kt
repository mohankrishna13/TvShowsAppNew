package com.mohankrishna.tvshowsapp.presentation_layer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel


class MainActivityViewModel(var commonRepositoryModel: CommonRepositoryModel):ViewModel() {
    fun searchTvShows(newText: String) = liveData{
        var responseListerner = commonRepositoryModel.getTvShowsByName(newText)
        emit(responseListerner)
    }
    fun getTrendingTvShowsData()= liveData{
        var responseListerner=
            commonRepositoryModel.getTrendingTvShowsData(BuildConfig.API_KEY)
        emit(responseListerner)
    }
    fun getWeekTvShowsData()= liveData{
            var responseListerner = commonRepositoryModel
                .getWeekTvShowsData(BuildConfig.API_KEY)
            emit(responseListerner)
    }
}