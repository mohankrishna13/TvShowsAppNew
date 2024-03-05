package com.mohankrishna.tvshowsapp.presentation_layer.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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