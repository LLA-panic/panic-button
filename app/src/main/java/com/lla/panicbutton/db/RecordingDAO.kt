package com.lla.panicbutton.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecording(recording: Recording)

    @Delete
    suspend fun deleteRecording(recording: Recording)

    @Query("SELECT * FROM recording_table ORDER BY dateAdded DESC")
    fun getAllRecordingsSortedByDateAdded(): LiveData<List<Recording>>

    @Query("SELECT * FROM recording_table ORDER BY audioTitle")
    fun getAllRecordingsSortedByAudioTitle(): LiveData<List<Recording>>
}