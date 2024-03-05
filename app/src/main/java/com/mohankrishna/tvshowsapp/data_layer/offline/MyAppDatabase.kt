package com.mohankrishna.tvshowsapp.data_layer.offline

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohankrishna.tvshowsapp.ModelClass.Result

@Database(entities = [Result::class], version = 1, exportSchema = true)
abstract class MyAppDatabase:RoomDatabase() {
    abstract fun getTvShowsDao():TvShowsDao
}