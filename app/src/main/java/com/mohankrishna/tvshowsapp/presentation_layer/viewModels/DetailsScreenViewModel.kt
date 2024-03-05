package com.mohankrishna.tvshowsapp.presentation_layer.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import com.mohankrishna.tvshowsapp.data_layer.online.retrofit.TvShowsApiInterface
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import com.mohankrishna.tvshowsapp.domain.repository.online.PagingRepository.PaginationDataSource
import kotlinx.coroutines.launch

class DetailsScreenViewModel(var commonRepositoryModel: CommonRepositoryModel):ViewModel() {
    var searchId= MutableLiveData<String>()
    fun setData(name:String){
        searchId.value=name
    }
    val moviesList = Pager(PagingConfig(1)) {
            PaginationDataSource(commonRepositoryModel,searchId.value!!.toInt())
    }.flow

    fun setFavouriteFlag(updatedData:Result){
        viewModelScope.launch {
           commonRepositoryModel.updateOfflineData(updatedData)
        }
    }

}