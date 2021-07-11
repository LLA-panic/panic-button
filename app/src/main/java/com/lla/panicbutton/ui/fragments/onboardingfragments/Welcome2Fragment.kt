package com.lla.panicbutton.ui.fragments.onboardingfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lla.panicbutton.R
import kotlinx.android.synthetic.main.fragment_welcome2.view.*

class Welcome2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_welcome2, container, false)

        view.welcomePanic.setOnClickListener {
            view.welcomePanic.visibility = View.INVISIBLE
        }

        view.welcomeBreath.setOnClickListener {
            view.welcomePanic.visibility = View.VISIBLE
        }

        return view
    }
}