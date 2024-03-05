package com.mohankrishna.tvshowsapp

import android.app.Application
import android.content.Context
import com.mohankrishna.tvshowsapp.data_layer.offline.provideDao
import com.mohankrishna.tvshowsapp.data_layer.offline.provideDatabaseInstance
import com.mohankrishna.tvshowsapp.data_layer.online.retrofit.getRefrofitInstance
import com.mohankrishna.tvshowsapp.domain.repository.commonRepository.CommonRepositoryModel
import com.mohankrishna.tvshowsapp.domain.repository.offline.LocalTvShowsRepoImpl
import com.mohankrishna.tvshowsapp.domain.repository.offline.LocalTvShowsRepository
import com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository.OnlineTvShowsRepoImpl
import com.mohankrishna.tvshowsapp.domain.repository.online.tvShowsRepository.OnlineTvShowsRepository
import com.mohankrishna.tvshowsapp.presentation_layer.utils.InternetModeProvider
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.DetailsScreenViewModel
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication:Application() {

    companion object {
        private var instance: MyApplication? = null
        fun getContext(): Context? {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()

        instance = this
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appUtils,
                retrofitModlues,
                databaseModule,
                commonRepositoryModule,
                viewmodelsModule))
        }
    }
    val appUtils= module {
        single { InternetModeProvider(get()) }
    }
    val databaseModule = module {
        single { provideDatabaseInstance(androidContext()) }
        single { provideDao(get()) }
        factory<LocalTvShowsRepository> { LocalTvShowsRepoImpl(get()) }
    }
    val retrofitModlues= module {
        single { getRefrofitInstance() }
        single<OnlineTvShowsRepository> { OnlineTvShowsRepoImpl(get(),get()) }
    }
    val commonRepositoryModule= module {
        single { CommonRepositoryModel(get(),get(),get()) }
    }
    val viewmodelsModule= module{
        factory { MainActivityViewModel(get()) }
        factory { DetailsScreenViewModel(get()) }
    }


}