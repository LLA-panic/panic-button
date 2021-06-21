package com.lla.panicbutton.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_starting_page.*

class StartingPageFragment : Fragment(R.layout.fragment_starting_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStartedButton.setOnClickListener {
            findNavController().navigate(R.id.action_startingPageFragment_to_welcomeFragment)
        }
    }
}