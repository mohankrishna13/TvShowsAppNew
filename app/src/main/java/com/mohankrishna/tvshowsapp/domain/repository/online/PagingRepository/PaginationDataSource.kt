package com.mohankrishna.tvshowsapp.domain.repository.online.PagingRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel

class PaginationDataSource(private var apiRepository:CommonRepositoryModel, var similarId:String,var typeOf:String): PagingSource<Int, Result>() {



    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            var responseData= mutableListOf<Result>()
            if(typeOf.compareTo("allTrending")==0){
                var dataCall=apiRepository.getTrendingTvShowsData(BuildConfig.API_KEY,currentPage)
                responseData.addAll(dataCall)
            }
            else if(typeOf.compareTo("weekData")==0){
                var dataCall=apiRepository.
                getWeekTvShowsData(BuildConfig.API_KEY,currentPage)
                responseData.addAll(dataCall.body()?.results?: emptyList())
            }
            else{
                var dataCall=apiRepository.
                getSimilarTypeData(similarId.toInt(),BuildConfig.API_KEY,currentPage)
                responseData.addAll(dataCall.body()?.results?: emptyList())

            }
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}