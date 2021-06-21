package com.lla.panicbutton.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_panic_button.*

class PanicButtonFragment : Fragment(R.layout.fragment_panic_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        panicButton.setOnClickListener {
            findNavController().navigate(R.id.action_panicButtonFragment_to_activatedPanicFragment)
        }
    }
}