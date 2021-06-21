package com.lla.panicbutton.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_activated_panic.*

@AndroidEntryPoint
class ActivatedPanicFragment : Fragment(R.layout.fragment_activated_panic) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        breatheButton.setOnClickListener {
            findNavController().navigate(R.id.action_activatedPanicFragment_to_panicButtonFragment)
        }
    }
}