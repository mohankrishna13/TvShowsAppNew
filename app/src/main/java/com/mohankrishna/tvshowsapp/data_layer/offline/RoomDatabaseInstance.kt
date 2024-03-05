package com.mohankrishna.tvshowsapp.data_layer.offline

import android.content.Context
import androidx.room.Room

fun provideDatabaseInstance(context: Context)=
    Room.databaseBuilder(context,
            MyAppDatabase::class.java,
            "MyRoomDatabase")
        .build()

fun provideDao(database: MyAppDatabase)=database.getTvShowsDao()