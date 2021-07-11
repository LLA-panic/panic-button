package com.lla.panicbutton.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lla.panicbutton.R
import com.lla.panicbutton.db.Episode
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import com.lla.panicbutton.util.Constants.SEVERE_ATTACK_TIME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_activated_panic.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ActivatedPanicFragment : Fragment(R.layout.fragment_activated_panic) {

    private val viewModel: MainViewModel by viewModels()
    private var startTimeInMillis = 0L

    @Inject
    lateinit var settings: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTimeInMillis = Calendar.getInstance().timeInMillis
        breatheButton.setOnClickListener {
            endEpisodeAndSaveToDb()
        }
    }

    private fun endEpisodeAndSaveToDb() {
        //val isSevere = curTimeInMillis >= getUserSevereTimeInMillis()
        val isSevere = false
        val dateTimestamp = Calendar.getInstance().timeInMillis
        var curTimeInMillis = Calendar.getInstance().timeInMillis - startTimeInMillis
        val episode = Episode(dateTimestamp, curTimeInMillis, isSevere)
        viewModel.insertEpisode(episode)
        Snackbar.make(
            this@ActivatedPanicFragment.requireView(),
            "Hope you're okay now!",
            Snackbar.LENGTH_LONG
        ).show()
        stopEpisode()
    }

    private fun stopEpisode() {
        findNavController().navigate(R.id.action_activatedPanicFragment_to_panicButtonFragment)
    }

    private fun getUserSevereTimeInMillis(): Long {
        val minutesChoices = resources.getStringArray(R.array.Minutes)
        var minutesInString = settings.getString(SEVERE_ATTACK_TIME, minutesChoices[0]) ?: "5"
        //TODO: change 10 minutes string to millis long
        var minutes = minutesInString.substring(0, 1).toLong()
        return TimeUnit.MINUTES.toMillis(minutes)
    }
}