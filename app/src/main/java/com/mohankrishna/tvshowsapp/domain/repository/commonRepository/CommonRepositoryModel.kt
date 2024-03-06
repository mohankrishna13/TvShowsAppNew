package com.mohankrishna.tvshowsapp.domain.repository.commonRepository
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
import kotlinx.coroutines.launch
import retrofit2.Response

class CommonRepositoryModel(var localTvShowsRepository: LocalTvShowsRepository,
                            var onlineTvShowsRepository: OnlineTvShowsRepository,
                            var internetModeProvider: InternetModeProvider) {


    fun updateOfflineData(id: Result) {
        CoroutineScope(Dispatchers.IO).launch{
           localTvShowsRepository.updateTvShowData(id)
        }
    }

    suspend fun getSimilarTypeData(id: Int, apiKey: String, currentPage: Int): Response<TvShowsDataModel> {
           return onlineTvShowsRepository.getSimilarTvShows(id,apiKey,currentPage)
    }

    suspend fun getWeekTvShowsData(api_key:String,pageNumber:Int):Response<TvShowsDataModel>{
        return  onlineTvShowsRepository.getWeekTrendingShows(api_key,pageNumber)

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

    suspend fun getTrendingTvShowsData(api_key:String,pageNumber:Int):Response<TvShowsDataModel>{
      return onlineTvShowsRepository.getTrendingTvShows(api_key,pageNumber)

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