package com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository

import androidx.lifecycle.MutableLiveData
import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import com.mohankrishna.tvshowsapp.data_layer.online.retrofit.TvShowsApiInterface
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.domain.repository.offline.LocalTvShowsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlineTvShowsRepoImpl(var tvShowsApiInterface: TvShowsApiInterface,
                            var localDatabaseDao: LocalTvShowsRepository): OnlineTvShowsRepository {
    override fun getTrendingTvShows(apiKey: String): MutableLiveData<ResponseListerner> {
        var result = MutableLiveData<ResponseListerner>()
        CoroutineScope(Dispatchers.IO).launch{
            var call = tvShowsApiInterface.getDayTrendingTvShows(apiKey)
            CoroutineScope(Dispatchers.Main).launch {
                result.value = (ResponseListerner.Loading())
            }
            call.enqueue(object : Callback<TvShowsDataModel> {
                override fun onResponse(
                    call: Call<TvShowsDataModel>,
                    response: Response<TvShowsDataModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.results.isEmpty()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                var data = localDatabaseDao.getTrendingTvShows()
                                data.collect {
                                    withContext(Dispatchers.Main) {
                                        result.value = (ResponseListerner.Success(it))
                                    }
                                }
                            }
                        } else {
                            var items = response.body()!!.results
                            for (item in items) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    localDatabaseDao.insertTvShowData(item)
                                }
                            }
                            var data = localDatabaseDao.getTrendingTvShows()
                            CoroutineScope(Dispatchers.IO).launch {
                                data.collect {
                                    withContext(Dispatchers.Main) {
                                        result.value = (ResponseListerner.Success(it))
                                    }
                                }
                            }
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch{
                            result.value = (ResponseListerner.Error(response.message()))
                        }
                    }
                }
                override fun onFailure(call: Call<TvShowsDataModel>, t: Throwable) {
                    CoroutineScope(Dispatchers.Main).launch {
                        result.value = (ResponseListerner.Failure(t))
                    }
                }
            })
        }
        return result
    }

    override suspend fun getWeekTrendingShows(apiKey: String): MutableLiveData<ResponseListerner> {
        var result = MutableLiveData<ResponseListerner>()
        CoroutineScope(Dispatchers.IO).launch {
            var call = tvShowsApiInterface.getWeekTvShows(apiKey)
            CoroutineScope(Dispatchers.Main).launch {
                result.value = (ResponseListerner.Loading())
            }
            call.enqueue(object : Callback<TvShowsDataModel> {
                override fun onResponse(
                    call: Call<TvShowsDataModel>,
                    response: Response<TvShowsDataModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.results.isEmpty()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                result.value = (ResponseListerner.Success(response.body()?.results))
                            }
                        } else {
                            var items = response.body()!!.results
                            for (item in items) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    localDatabaseDao.insertTvShowData(item)
                                }
                            }
                            var data = localDatabaseDao.getTrendingTvShows()
                            CoroutineScope(Dispatchers.IO).launch {
                                data.collect {
                                    withContext(Dispatchers.Main) {
                                        result.value = (ResponseListerner.Success(it))
                                    }
                                }
                            }
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch{
                            result.value = (ResponseListerner.Error(response.message()))
                        }
                    }
                }

                override fun onFailure(call: Call<TvShowsDataModel>, t: Throwable) {
                    CoroutineScope(Dispatchers.IO).launch{
                        result.value = (ResponseListerner.Failure(t))
                    }
                }
            })
        }
        return result
    }

    override suspend fun getSimilarTvShows(id: Int, apiKey: String, currentPage: Int):
            Response<TvShowsDataModel> {
        return tvShowsApiInterface.getSimilarTvShows(id, apiKey, currentPage)
    }

    override  fun getTvShowsByName(apiKey: String, searchKey: String):
            MutableLiveData<ResponseListerner> {
        var result = MutableLiveData<ResponseListerner>()
        CoroutineScope(Dispatchers.IO).launch{
            var call = tvShowsApiInterface.getTvShowsByName(apiKey,searchKey)
            CoroutineScope(Dispatchers.Main).launch {
                result.value = (ResponseListerner.Loading())
            }
            call.enqueue(object : Callback<TvShowsDataModel> {
                override fun onResponse(
                    call: Call<TvShowsDataModel>,
                    response: Response<TvShowsDataModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.results.isEmpty()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                result.value = (ResponseListerner.Success(response.body()?.results))
                            }
                        } else {
                            var items = response.body()!!.results
                            CoroutineScope(Dispatchers.Main).launch {
                                result.value = (ResponseListerner.Success(items))
                            }
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            result.value = (ResponseListerner.Error(response.message()))
                        }
                    }
                }

                override fun onFailure(call: Call<TvShowsDataModel>, t: Throwable) {
                    CoroutineScope(Dispatchers.Main).launch{
                        result.value = (ResponseListerner.Failure(t))
                    }
                }
            })
        }
        return result
    }
}

//Another way emit result
/*
        return suspendCoroutine {
            data->
            run {
                call.enqueue(object : Callback<TvShowsDataModel> {
                    override fun onResponse(
                        call: Call<TvShowsDataModel>,
                        response: Response<TvShowsDataModel>
                    ) {
                        if (response.isSuccessful) {
                            data.resume(ResponseListernes.Success(response.body()?.results))
                        } else {
                            data.resume(ResponseListernes.Error(response.message()))
                        }
                    }
                    override fun onFailure(call: Call<TvShowsDataModel>, t: Throwable) {
                        data.resume(ResponseListernes.Failure(t))
                    }
                })
            }
        }
*/
