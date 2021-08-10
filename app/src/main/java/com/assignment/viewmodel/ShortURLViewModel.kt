package com.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.model.UIState
import com.assignment.usecase.CreateShortURLUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortURLViewModel @Inject constructor(
    private val createShortURLUseCase: CreateShortURLUseCase
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData<UIState>()
    val uiStateLiveData : LiveData<UIState> = _uiStateLiveData

    fun createShortUrl(url: String) {
        _uiStateLiveData.value = UIState.ShowInfo

        viewModelScope.launch {
            try {
                val response = createShortURLUseCase.create(url)
                _uiStateLiveData.value = UIState.Success(response)
            }
            catch (e: Exception) {
                e.printStackTrace()
                _uiStateLiveData.value = UIState.Error(e)
            }
        }
    }
}