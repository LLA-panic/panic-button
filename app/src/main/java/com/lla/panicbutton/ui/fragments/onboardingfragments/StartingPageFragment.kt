package com.lla.panicbutton.ui.fragments.onboardingfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import com.lla.panicbutton.util.Constants.IS_FIRST_TIME
import com.lla.panicbutton.util.Constants.ON_BOARDING
import kotlinx.android.synthetic.main.fragment_starting_page.view.*

class StartingPageFragment : Fragment(R.layout.fragment_starting_page) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_starting_page, container, false)

        if (isOnBoardingFinished()) {
            findNavController().navigate(R.id.action_startingPageFragment_to_panicButtonFragment)
        }

        view.getStartedButton.setOnClickListener {
            findNavController().navigate(R.id.action_startingPageFragment_to_welcome1Fragment)
        }

        return view
    }

    private fun isOnBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(IS_FIRST_TIME, false)
    }
}