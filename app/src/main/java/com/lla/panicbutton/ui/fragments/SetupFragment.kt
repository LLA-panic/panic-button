package com.lla.panicbutton.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lla.panicbutton.R
import com.lla.panicbutton.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    private val viewModel: MainViewModel by viewModels()
}