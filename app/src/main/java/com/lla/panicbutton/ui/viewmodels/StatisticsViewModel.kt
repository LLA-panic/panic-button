package com.lla.panicbutton.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.lla.panicbutton.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    val mainRepository: MainRepository
): ViewModel() {

}