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
import com.lla.panicbutton.util.Constants.IS_FIRST_TIME
import com.lla.panicbutton.util.Constants.ON_BOARDING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_upload.*

@AndroidEntryPoint
class UploadFragment : Fragment(R.layout.fragment_upload) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isOnBoardingFinished()) {
            uploadNextButton.visibility = View.GONE
            uploadDoneButton.visibility = View.VISIBLE
        }

        uploadNextButton.setOnClickListener {
            findNavController().navigate(R.id.action_uploadFragment_to_panicButtonFragment)
            setOnBoardingFinished()
        }

        uploadDoneButton.setOnClickListener {
            findNavController().navigate(R.id.action_uploadFragment_to_recordingsFragment)
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

    private fun setOnBoardingFinished() {
        val sharedPref =
            requireActivity().getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(IS_FIRST_TIME, true)
        editor.apply()
    }

    private fun isOnBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(IS_FIRST_TIME, false)
    }
}