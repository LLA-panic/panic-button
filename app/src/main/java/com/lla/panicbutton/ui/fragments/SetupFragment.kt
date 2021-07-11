package com.lla.panicbutton.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setup.*


@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(onBoardingButton()) {
            nextButton.visibility = View.GONE
            doneButton2.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_panicButtonFragment)
        }

        doneButton2.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_recordingsFragment)
        }

        yourPhoneButton.setOnClickListener {
        }
    }

    private suspend fun loadAudioFromInternalStorage() {
        val intent: Intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "audio/mpeg"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                val audioFileUri: Uri? = data.data
                // Now you can use that Uri to get the file path, or upload it, ...
            }
        }
    }

    private fun onBoardingButton() : Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoardingNext", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("setUpNext", false)
    }
}