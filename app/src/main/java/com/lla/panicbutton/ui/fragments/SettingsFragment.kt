package com.lla.panicbutton.ui.fragments

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinner()

        doneButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_panicButtonFragment)
        }

        //TODO: save it to the db, and load the phone number everytime the user comes back to this screen
        editTextPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
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
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsFragment.requireContext(),
                    minutesChoices[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsFragment.requireContext(),
                    minutesChoices[0], Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}