package com.assignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.assignment.databinding.FragmentHomeBinding
import com.assignment.model.ShortUrlModel
import com.assignment.model.UIState
import com.assignment.ui.history.HistoryClickListener
import com.assignment.ui.history.HistoryPagingAdapter
import com.assignment.usecase.CopyToClipboardUseCase
import com.assignment.viewmodel.HistoryViewModel
import com.assignment.viewmodel.ShortURLViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HistoryClickListener {

    private val shortURLViewModel: ShortURLViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()


    @Inject
    lateinit var copyToClipboardUseCase: CopyToClipboardUseCase

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HistoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.buttonShorten.setOnClickListener {
            if (binding.edittextLink.text.toString().isNotEmpty()) {
                shortURLViewModel.createShortUrl(binding.edittextLink.text.toString())
            }
        }
        adapter = HistoryPagingAdapter(
            historyClickListener = this,
            copyToClipboardUseCase = copyToClipboardUseCase
        )
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shortURLViewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer {
            processUIState(it)
        })

        lifecycleScope.launch {
            historyViewModel.historyStateFlow.collectLatest { it ->
                binding.infoContainer.visibility = if(it is UIState.ShowInfo) View.VISIBLE else View.GONE
                binding.historyContainer.visibility = View.VISIBLE

                when (it) {
                    is UIState.ShowInfo -> {
                    }
                    is UIState.Success -> {
                        adapter.submitData(it.data as PagingData<ShortUrlModel>)
                    }
                    else -> {

                    }
                }
            }
        }
        loadHistory()
    }

    private fun processUIState(uiState: UIState) {
        when (uiState) {
            is UIState.ShowInfo -> {
            }
            is UIState.Success -> {
            }
            else -> {
            }
        }
    }

    private fun loadHistory() {
        historyViewModel.getHistory()
    }

    override fun onClick(shortUrlModel: ShortUrlModel) {
        historyViewModel.deleteHistory(shortUrlModel.id)
    }
}