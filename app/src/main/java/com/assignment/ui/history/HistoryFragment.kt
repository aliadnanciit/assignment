package com.assignment.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.assignment.databinding.FragmentHistoryBinding
import com.assignment.model.ShortUrlModel
import com.assignment.model.UIState
import com.assignment.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        adapter = HistoryPagingAdapter()
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyStateFlow.collectLatest { it ->
                    when (it) {
                        is UIState.Loading -> {

                        }
                        is UIState.Success -> {
                            adapter.submitData(it.data as PagingData<ShortUrlModel>)
                        }
                        else -> {

                        }
                    }
                }
//            }
        }
        loadHistory()
    }

    private fun loadHistory() {
        viewModel.getHistory()
    }
}