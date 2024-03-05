package com.mohankrishna.tvshowsapp.domain.repository.online.PagingRepository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaginationDataSource(private var apiRepository:CommonRepositoryModel,var id:Int):
    PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            var responseData= mutableListOf<Result>()
            var dataCall=apiRepository.
            getSimilarTypeData(id,BuildConfig.API_KEY,currentPage)
            responseData.addAll(dataCall.body()?.results?: emptyList())
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e:Exception){
            Log.e("PrintData",e.toString())
            LoadResult.Error(e)
        }
    }
}