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
import com.lla.panicbutton.db.Recording
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import com.lla.panicbutton.util.Constants
import com.lla.panicbutton.util.Constants.DIRECTORY_RECORDINGS
import com.lla.panicbutton.util.Constants.IS_FIRST_TIME
import com.lla.panicbutton.util.Constants.ON_BOARDING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UploadFragment : Fragment(R.layout.fragment_upload) {

    private val viewModel: MainViewModel by viewModels()
    lateinit var recordingsDir: File

    @Inject
    lateinit var settings: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordingsDir = createRecordingsDirInStorage(this.requireContext())

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
                val size = getFileNameAndSize(audioFileUri).second
                if (validSize(size) && audioFileUri.path != null) {
                    val audioLength = getAudioLengthMillis(this.requireContext(), audioFileUri)
                    val uploadedRecording = Recording(fileName, audioLength, Calendar.getInstance().timeInMillis)
                    viewModel.insertRecording(uploadedRecording)
                    //saving to the app-specific dir
                    saveFile(audioFileUri, recordingsDir)
                    Snackbar.make(
                        this@UploadFragment.requireView(),
                        "Audio $fileName uploaded.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        this@UploadFragment.requireView(),
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

    private fun getAudioLengthMillis(context: Context, uri: Uri): Long {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, uri)
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
            .toLong()
    }

    private fun createRecordingsDirInStorage(context: Context): File {
        // we are creating the dir where we'll be storing the user's recordings and returning it to write to it
        val recordingsDir = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_MUSIC
            ), DIRECTORY_RECORDINGS
        )
        if (!recordingsDir.mkdirs()) {
            Timber.d("${Constants.WARN_LOG_TAG} Directory not created")
        }
        return recordingsDir
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

    private fun saveFile(sourceUri: Uri, directory: File) {
        val sourceFilename: String = sourceUri.path!!
        val destinationFilename =
            directory.path + File.separatorChar + getFileNameAndSize(sourceUri).first
        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            bis = BufferedInputStream(FileInputStream(sourceFilename))
            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
            val buf = ByteArray(1024)
            bis.read(buf)
            do {
                bos.write(buf)
            } while (bis.read(buf) != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bis?.close()
                bos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}