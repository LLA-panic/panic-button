package com.lla.panicbutton.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lla.panicbutton.db.Episode
import com.lla.panicbutton.db.Recording
import com.lla.panicbutton.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    private val recordingsSortedByDate = mainRepository.getAllRecordingsSortedByDateAdded()
    private val recordingsSortedByTitle = mainRepository.getAllRecordingsSortedByAudioTitle()
    private val recordingsSortedByLength = mainRepository.getAllRecordingsSortedByTimeInMillis()

    private val episodesSortedByDate = mainRepository.getAllEpisodesSortedByDataAdded()
    private val episodesSortedByLength = mainRepository.getAllEpisodesSortedByTimeInMillis()


    fun insertEpisode(episode: Episode) = viewModelScope.launch {
        mainRepository.insertEpisode(episode)
    }

    fun insertRecording(recording: Recording) = viewModelScope.launch {
        mainRepository.insertRecording(recording)
    }

    fun recordings(): LiveData<List<Recording>> = recordingsSortedByDate
}