package com.mohankrishna.tvshowsapp.data_layer.online.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getRefrofitInstance()= Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(getClient())
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .build()
    .create(TvShowsApiInterface::class.java)

fun getClient()=
    OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(getloggingInterceptor())
        .build()

fun getloggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}