package com.lla.panicbutton.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lla.panicbutton.R
import com.lla.panicbutton.adapters.RecordingAdapter
import com.lla.panicbutton.db.Recording
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recordings.*

@AndroidEntryPoint
class RecordingsFragment : Fragment(R.layout.fragment_recordings) {

    private lateinit var recordingAdapter: RecordingAdapter

    private val viewModel: MainViewModel by viewModels()

    //TODO: change this to get the actual recordings the user added
    var recordingList = mutableListOf(
        Recording("Message from mom", 240000),
        Recording("Bird sounds", 198000),
        Recording("Ocean during storm", 3734000),
        Recording("Love from Sosoon", 240000),
        Recording("Thunder", 240000)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        uploadButton.setOnClickListener {
            findNavController().navigate(R.id.action_recordingsFragment_to_uploadFragment)
        }
    }

    private fun setupRecyclerView() = rvRecordings.apply {
        recordingAdapter = RecordingAdapter(recordingList)
        adapter = recordingAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}