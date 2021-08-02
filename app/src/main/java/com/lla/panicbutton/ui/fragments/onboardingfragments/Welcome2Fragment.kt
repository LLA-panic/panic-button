package com.lla.panicbutton.ui.fragments.onboardingfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_welcome2.view.*

class Welcome2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_welcome2, container, false)

        view.welcome2PanicButton.setOnClickListener {
            view.welcome2PanicButton.visibility = View.INVISIBLE
        }

        view.welcome2BreathButton.setOnClickListener {
            view.welcome2BreathButton.visibility = View.VISIBLE
        }

        return view
    }
}