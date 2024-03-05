package com.mohankrishna.tvshowsapp.domain.model

import com.mohankrishna.tvshowsapp.ModelClass.Result

sealed class ResponseListerner{
    class Loading() : ResponseListerner()
    data class Success(val data: List<Result>?) : ResponseListerner()
    data class Failure(val error: Throwable) : ResponseListerner()
    data class Error(val error: String) : ResponseListerner()
}