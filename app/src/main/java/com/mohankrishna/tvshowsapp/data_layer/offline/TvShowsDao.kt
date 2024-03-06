package com.mohankrishna.tvshowsapp.data_layer.offline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohankrishna.tvshowsapp.ModelClass.Result
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShowsDao {
    @Query("Select * From myshowstable")
    fun getTrendingTvShows(): Flow<List<Result>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShow(tvshow:Result)

    @Update
    fun updateData(tvshow: Result)

    @Query("SELECT * FROM MyShowsTable WHERE name LIKE '%' || :it || '%'")
    fun getTvShowsByName(it: String?):Flow<List<Result>>

    @Query("SELECT * FROM MyShowsTable LIMIT :limit OFFSET :offset")
    suspend fun getTvShowsDataByLimit(limit: Int, offset: Int): List<Result>
}