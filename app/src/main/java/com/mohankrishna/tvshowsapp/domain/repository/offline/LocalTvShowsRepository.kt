package com.mohankrishna.tvshowsapp.domain.repository.offline

import com.mohankrishna.tvshowsapp.ModelClass.Result
import kotlinx.coroutines.flow.Flow

interface LocalTvShowsRepository {
    fun getTrendingTvShows(): Flow<List<Result>>
    fun insertTvShowData(tvShowsData: Result)
    fun updateTvShowData(tvShowsData: Result)
    fun getTvShowDataByName(tvShowsData: String):Flow<List<Result>>
}
