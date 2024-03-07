package com.mohankrishna.tvshowsapp.domain.repository.commonRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.domain.repository.offline.LocalTvShowsRepository
import com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository.OnlineTvShowsRepository
import com.mohankrishna.tvshowsapp.presentation_layer.utils.InternetModeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import retrofit2.Response

class CommonRepositoryModel(var localTvShowsRepository: LocalTvShowsRepository, var onlineTvShowsRepository: OnlineTvShowsRepository, var internetModeProvider: InternetModeProvider) {

    fun updateOfflineData(id: Result) {
        CoroutineScope(Dispatchers.IO).launch{
           localTvShowsRepository.updateTvShowData(id)
        }
    }

    suspend fun getSimilarTypeData(id: Int, apiKey: String, currentPage: Int): Response<TvShowsDataModel> {
           return onlineTvShowsRepository.getSimilarTvShows(id,apiKey,currentPage)
    }

    suspend fun getWeekTvShowsData(api_key:String,pageNumber:Int):List<Result>{
        if(internetModeProvider.isNetworkConnected){
            var response=
                onlineTvShowsRepository.getWeekTrendingShows(api_key,pageNumber)
            if(response.isSuccessful){
                var resultList=response.body()?.results
                if(resultList!=null){
                    for(item in resultList!!){
                        localTvShowsRepository.insertTvShowData(item)
                    }
                }
                var limit=20
                var offset=pageNumber*limit

                return localTvShowsRepository.getTvShowsDataByLimit(limit,offset)
            }else{
                return emptyList()
            }
        }else{
            var limit=20
            var offset=pageNumber*limit
            return localTvShowsRepository.getTvShowsDataByLimit(limit,offset)
        }
       // return  onlineTvShowsRepository.getWeekTrendingShows(api_key,pageNumber)

    /*var response= MutableLiveData<ResponseListerner>()
        if(internetModeProvider.isNetworkConnected){
            response= onlineTvShowsRepository.getWeekTrendingShows(api_key)
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                localTvShowsRepository.getTrendingTvShows().collect{
                    withContext(Dispatchers.Main){
                        response.value= ResponseListerner.Success(it)
                    }
                }
            }
        }
        return response*/
    }

    suspend fun getTrendingTvShowsData(api_key:String,pageNumber:Int): List<Result> {
        if(internetModeProvider.isNetworkConnected){
            var response=onlineTvShowsRepository.getTrendingTvShows(api_key,pageNumber)
            if(response.isSuccessful){
                var resultList=response.body()?.results
                if(resultList!=null){
                    for(item in resultList!!){
                        localTvShowsRepository.insertTvShowData(item)
                    }
                }
                var limit=20
                var offset=pageNumber*limit
                var data=localTvShowsRepository.getTvShowsDataByLimit(limit,offset)
                if(data.isEmpty()){
                    data=resultList!!
                }
                return data
            }else{
                return emptyList()
            }
        }else{
            var limit=20
            var offset=pageNumber*limit

            return localTvShowsRepository.getTvShowsDataByLimit(limit,offset)
        }
      //Without pagination Don't delete this code
       /* var response= MutableLiveData<ResponseListerner>()
        if(internetModeProvider.isNetworkConnected){
            response= onlineTvShowsRepository.getTrendingTvShows(api_key,pageNumber)
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                localTvShowsRepository.getTrendingTvShows().collect{
                    withContext(Dispatchers.Main){
                        response.value= ResponseListerner.Success(it)
                    }
                }
            }
        }
        return response*/
    }

    fun getTvShowsByName(searchKey:String):MutableLiveData<ResponseListerner>{
        var response=MutableLiveData<ResponseListerner>()
       if(internetModeProvider.isNetworkConnected){
            response= onlineTvShowsRepository.getTvShowsByName(BuildConfig.API_KEY,searchKey)
       } else {
           var colleciton = localTvShowsRepository.getTvShowDataByName(searchKey)
           CoroutineScope(Dispatchers.Main).launch {
               colleciton.collect {
                   response.value = ResponseListerner.Success(it)
               }
           }
           /* CoroutineScope(Dispatchers.IO).launch {
                 .collect{
                     withContext(Dispatchers.Main){
                         response.value= ResponseListerner.Success(it)

                     }
                 }

        }*/
       }
        return response
    }
}