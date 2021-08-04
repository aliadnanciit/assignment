package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherSettingViewModel @Inject constructor(
) : ViewModel() {

    val showTempInDegree = MutableLiveData(true)

    fun toggleSelection() {
        showTempInDegree.value = showTempInDegree.value!!.not()
    }
}