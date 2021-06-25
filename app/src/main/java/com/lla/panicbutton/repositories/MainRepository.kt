package com.lla.panicbutton.repositories

import com.lla.panicbutton.db.Episode
import com.lla.panicbutton.db.EpisodeDAO
import com.lla.panicbutton.db.Recording
import com.lla.panicbutton.db.RecordingDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val recordingDAO: RecordingDAO,
    val episodeDao: EpisodeDAO
){
    suspend fun insertRecording(recording: Recording) = recordingDAO.insertRecording(recording)
    suspend fun deleteRecording(recording: Recording) = recordingDAO.deleteRecording(recording)
    fun getAllRecordingsSortedByDateAdded() = recordingDAO.getAllRecordingsSortedByDateAdded()
    fun getAllRecordingsSortedByAudioTitle() = recordingDAO.getAllRecordingsSortedByAudioTitle()
    fun getAllRecordingsSortedByTimeInMillis() = recordingDAO.getAllRecordingsSortedByTimeInMillis()

    suspend fun insertEpisode(episode: Episode) = episodeDao.insertEpisode(episode)
    suspend fun deleteEpisode(episode: Episode) = episodeDao.deleteEpisode(episode)
    fun getAllEpisodesSortedByDataAdded() = episodeDao.getAllEpisodesSortedByDateAdded()
    fun getAllEpisodesSortedByTimeInMillis() =  episodeDao.getAllEpisodesSortedByTimeInMillis()
    fun getTotalEpisodesTimeInMillis() = episodeDao.getTotalTimeInMillis()

}