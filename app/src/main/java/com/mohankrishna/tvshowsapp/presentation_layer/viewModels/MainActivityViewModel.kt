package com.mohankrishna.tvshowsapp.presentation_layer.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohankrishna.tvshowsapp.BuildConfig
import com.mohankrishna.tvshowsapp.domain.model.ResponseListerner
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import kotlinx.coroutines.launch

class MainActivityViewModel(var commonRepositoryModel: CommonRepositoryModel):ViewModel() {
    var data=MutableLiveData<ResponseListerner>()
    fun getTrendingTvShowsData(){
        viewModelScope.launch {
           var responseListerner=
               commonRepositoryModel.getTrendingTvShowsData(BuildConfig.API_KEY)
            data=responseListerner
        }
    }
    fun getWeekTvShowsData(){
        viewModelScope.launch {
            var responseListerner = commonRepositoryModel.getWeekTvShowsData(BuildConfig.API_KEY)
            observerTheData(responseListerner)
        }
    }
    fun searchTvShows(newText: String) {
       viewModelScope.launch {
              var responseListerner = commonRepositoryModel.getTvShowsByName(newText)

            observerTheData(responseListerner)
       }
    }
    fun observerTheData(responseListerner: MutableLiveData<ResponseListerner>) {
        responseListerner.observeForever(Observer {
            when(it){
                is ResponseListerner.Error->{
                    data.value=ResponseListerner.Error(it.error)
                }
                is ResponseListerner.Failure->{
                    data.value = ResponseListerner.Failure(it.error)
                }
                is ResponseListerner.Success->{
                    data.value = ResponseListerner.Success(it.data)
                }is ResponseListerner.Loading->{
                data.value=ResponseListerner.Loading()
            }
            }
        })

    }


}