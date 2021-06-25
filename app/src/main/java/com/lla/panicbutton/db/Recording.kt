package com.lla.panicbutton.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recording_table")
data class Recording(
    var audioTitle: String,
    var timeInMillis: Long = 0L,
    var dateAdded: Long = 0L
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}