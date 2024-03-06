package com.mohankrishna.tvshowsapp.data_layer.online.retrofit

import com.mohankrishna.tvshowsapp.ModelClass.TvShowsDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TvShowsApiInterface {
    @GET("trending/tv/day?")
    suspend fun getDayTrendingTvShows(@Query("page") page : Int,
                              @Query("api_key") api_key : String)
    :Response<TvShowsDataModel>

    @GET("tv/{series_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("series_id") series_id : Int,
        @Query("api_key") api_key : String,
        @Query("page") page : Int): Response<TvShowsDataModel>

    @GET("trending/tv/week?")
    suspend fun getWeekTvShows(@Query("api_key") api_key : String,@Query("page") page : Int): Response<TvShowsDataModel>

    @GET("search/tv?")
     fun getTvShowsByName(
        @Query("api_key") api_key : String,
                          @Query("query") name:String
                          ): Call<TvShowsDataModel>

}