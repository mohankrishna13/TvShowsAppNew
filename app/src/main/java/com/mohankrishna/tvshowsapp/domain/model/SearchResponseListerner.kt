package com.mohankrishna.tvshowsapp.domain.model

import com.mohankrishna.tvshowsapp.ModelClass.Result

sealed class SearchResponseListerner{
    class Loading() : SearchResponseListerner()
    data class Success(val data: List<Result>?) : SearchResponseListerner()
    data class Failure(val error: Throwable) : SearchResponseListerner()
    data class Error(val error: String) : SearchResponseListerner()
}