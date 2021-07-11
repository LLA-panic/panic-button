package com.lla.panicbutton.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.lla.panicbutton.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.lla.panicbutton.util.Constants.ACTION_STOP_SERVICE
import timber.log.Timber

class RecordingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /* TODO allows the user to record audio file, we need to setup write permissions
            and save the audio to app container and set up a new fragment to support recording.
            NOT priority right now!
        */
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Timber.d("Started or resumed service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}