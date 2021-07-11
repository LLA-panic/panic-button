package com.lla.panicbutton.ui.fragments.onboardingfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_welcome1.view.*

class Welcome1Fragment : Fragment(R.layout.fragment_welcome1) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome1, container, false)

        view.welcome1NextButton.setOnClickListener{
            findNavController().navigate(R.id.action_welcome1Fragment_to_viewPagerFragment)
        }

        return view
    }
}