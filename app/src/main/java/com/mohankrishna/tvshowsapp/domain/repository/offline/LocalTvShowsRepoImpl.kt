package com.mohankrishna.tvshowsapp.domain.repository.offline

import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.data_layer.offline.TvShowsDao
import kotlinx.coroutines.flow.Flow

class LocalTvShowsRepoImpl(var tvShowsDao: TvShowsDao):LocalTvShowsRepository {
    override fun getTrendingTvShows(): Flow<List<Result>> {
        return tvShowsDao.getTrendingTvShows()
    }

    override fun insertTvShowData(tvShowsData: Result) {
        tvShowsDao.insertTvShow(tvShowsData)
    }

    override fun updateTvShowData(tvShowsData: Result) {
        tvShowsDao.updateData(tvShowsData)
    }

    override fun getTvShowDataByName(tvShowsData: String):Flow<List<Result>> {
        return tvShowsDao.getTvShowsByName(tvShowsData)
    }
}