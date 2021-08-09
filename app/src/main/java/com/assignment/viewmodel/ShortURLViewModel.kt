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
        _uiStateLiveData.value = UIState.Loading

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


    fun fetchPages() {
//        val flow = Pager(PagingConfig(20)) {
//            ExamplePagingSource(createShortURLUseCase, "dubai")
//        }.flow
//            .launchIn(viewModelScope)
    }
}

//class ExamplePagingSource(
//    private val getHistoryUseCase: com.assignment.usecase.GetHistoryUseCase,
//    val city: String
//): PagingSource<Int, com.assignment.model.ListItem>() {
//    override fun getRefreshKey(state: PagingState<Int, com.assignment.model.ListItem>): Int? {
//        return state.anchorPosition?.plus(1)
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.assignment.model.ListItem> {
//        return try {
//            val response = getHistoryUseCase.execute(city)
//            LoadResult.Page(data = response.list!!, prevKey = null, nextKey = 1)
//        } catch (e: java.lang.Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}