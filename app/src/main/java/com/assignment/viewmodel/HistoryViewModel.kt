package com.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.model.UIState
import com.assignment.usecase.DeleteHistoryUseCase
import com.assignment.usecase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : ViewModel() {

    private val _historyStateFlow = MutableStateFlow<UIState>(UIState.Loading)
    val historyStateFlow: StateFlow<UIState> = _historyStateFlow

    fun getHistory() {
        viewModelScope.launch {
            getHistoryUseCase.getHistory()
//                .cachedIn(viewModelScope)
                .collect {
                    _historyStateFlow.value = UIState.Success(it)
                }

        }
    }

    fun deleteHistory(id: Int) {
        viewModelScope.launch {
            deleteHistoryUseCase.delete(id)
        }
    }
}