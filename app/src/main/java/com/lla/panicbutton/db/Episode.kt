package com.lla.panicbutton.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class Episode(
    var dateAdded: Long = 0L,
    var timeInMillis: Long = 0L,
    var isSevere: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
