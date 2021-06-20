package com.lla.panicbutton.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recording::class, Episode::class],
    version = 1
)
abstract class PanicDatabase : RoomDatabase() {

    abstract fun getEpisodeDao(): EpisodeDAO
    abstract fun getRecodingDao(): RecordingDAO
}