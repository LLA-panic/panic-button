package com.lla.panicbutton.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lla.panicbutton.R
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import com.lla.panicbutton.util.Constants
import com.lla.panicbutton.util.Constants.DIRECTORY_RECORDINGS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.fragment_welcome.nextButton
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    private val viewModel: MainViewModel by viewModels()
    lateinit var recordingsDir: File

    @Inject
    lateinit var settings: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordingsDir = createRecordingsDirInStorage(this.requireContext(), DIRECTORY_RECORDINGS)

        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_panicButtonFragment)
        }

        yourPhoneButton.setOnClickListener {
            lifecycleScope.launch {
                loadAudioFromInternalStorage()
            }
        }
    }

    private suspend fun loadAudioFromInternalStorage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "audio/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                //TODO: remove all !!
                val audioFileUri: Uri = data.data!!
                val fileName = getFileNameAndSize(audioFileUri).first
                var size = getFileNameAndSize(audioFileUri).second
                if (validSize(size)) {
                    var audioLength = audioFileUri.path?.let { getAudioLengthMillis(it) }
                    //var uploadedRecording = Recording(fileName, length, Calendar.getInstance().timeInMillis)
                    Snackbar.make(
                        this@SetupFragment.requireView(),
                        "Audio $fileName uploaded.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        this@SetupFragment.requireView(),
                        "Audio file was too big to upload.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun getFileNameAndSize(uri: Uri): Pair<String, Long> {
        var name = ""
        var size = 0L
        if (uri.scheme == "content") {
            val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE))
                }
            }
        }
        if (name.isEmpty() || size == 0L) {
            //TODO: should we throw errors or log warnings?
        }
        return Pair(name, size)
    }

    private fun validSize(size: Long): Boolean {
        //TODO: add max file size!
        return true
    }

    private fun getAudioLengthMillis(path: String): Long {
        //TODO: this doesn't work to get the audio's length
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(path)
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
            .toLong()
    }

    //TODO: doesn't work
    private fun createRecordingsDirInStorage(context: Context, folderName: String): File {
        // we are creating the dir where we'll be storing the user's recordings and returning it to write to it
        val recordingsDir = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_MUSIC
            ), folderName
        )
        if (!recordingsDir.mkdirs()) {
            Timber.d("${Constants.WARN_LOG_TAG} Directory not created")
        }
        return recordingsDir
    }

}