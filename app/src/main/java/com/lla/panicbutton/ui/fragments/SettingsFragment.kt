package com.lla.panicbutton.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lla.panicbutton.R
import com.lla.panicbutton.util.Constants.EMERGENCY_PHONE
import com.lla.panicbutton.util.Constants.SEVERE_ATTACK_TIME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var settings: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinner()
        getPreviousSettings()

        editTextPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        doneButton.setOnClickListener {
            saveSettings()
            findNavController().navigate(R.id.action_settingsFragment_to_panicButtonFragment)
        }
    }

    private fun getPreviousSettings() {
        val minutesChoices = resources.getStringArray(R.array.Minutes)
        editTextPhone.setText(settings.getString(EMERGENCY_PHONE, ""))
        spinner.setSelection(
            minutesChoices.indexOf(
                settings.getString(
                    SEVERE_ATTACK_TIME,
                    minutesChoices[0]
                )
            )
        )
    }

    private fun saveSettings() {
        settings.edit()
            .putString(EMERGENCY_PHONE, editTextPhone.text.toString())
            .putString(SEVERE_ATTACK_TIME, spinner.selectedItem.toString())
            .apply()

        Snackbar.make(
            this@SettingsFragment.requireView(),
            "Settings saved.",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setUpSpinner() {
        val minutesChoices = resources.getStringArray(R.array.Minutes)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this.requireContext(),
                R.layout.item_minutes,
                minutesChoices
            )
            spinner.adapter = adapter
        }
    }
}