package com.assignment.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.assignment.databinding.FragmentUrlHistoryBinding
import com.assignment.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: FragmentUrlHistoryBinding
    private lateinit var adapter: HistoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUrlHistoryBinding.inflate(layoutInflater)

        adapter = HistoryPagingAdapter()

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, Observer { forecastWeather ->
//
//        })
        loadForecastWeatherData()
    }

    private fun loadForecastWeatherData() {
//        viewModel.fetchForecastWeather("")
    }

//    private fun showDate(city: String, list: List<com.assignment.model.ListItem>) {
//        adapter.submitList(list)
//    }
}