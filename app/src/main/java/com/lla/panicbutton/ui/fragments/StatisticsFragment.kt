package com.lla.panicbutton.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lla.panicbutton.R
import com.lla.panicbutton.ui.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()
}