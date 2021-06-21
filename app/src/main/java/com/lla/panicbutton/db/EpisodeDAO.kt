package com.lla.panicbutton.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EpisodeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Delete
    suspend fun deleteEpisode(recording: Episode)

    @Query("SELECT * FROM episode_table ORDER BY dateAdded DESC")
    fun getAllEpisodesSortedByDateAdded(): LiveData<List<Episode>>

    @Query("SELECT * FROM episode_table ORDER BY timeInMillis DESC")
    fun getAllEpisodesSortedByTimeInMillis(): LiveData<List<Episode>>

    @Query("SELECT SUM(timeInMillis) FROM episode_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    //TODO: getPastMonthTimeInMillis and getPastWeekTimeInMillis for stats?
}