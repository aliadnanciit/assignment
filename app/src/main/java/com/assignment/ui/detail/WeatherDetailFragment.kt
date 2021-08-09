package com.assignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.assignment.databinding.FragmentForecastWeatherDetailBinding
import com.assignment.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    @Inject
    lateinit var temperatureUtil: com.assignment.common.TemperatureUtil

    private val viewModel: WeatherViewModel by viewModels()

    private var paramCity: String? = null
    private lateinit var binding: FragmentForecastWeatherDetailBinding
    private lateinit var adapter: ForecastWeatherListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastWeatherDetailBinding.inflate(layoutInflater)

        adapter = ForecastWeatherListAdapter(
            temperatureUtil,
            true
        )

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if(it.getString("city") != "none") {
                paramCity = it.getString("city")!!
            }
        }

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, Observer { forecastWeather ->

        })
        loadForecastWeatherData(paramCity)
    }

    private fun loadForecastWeatherData(city: String?) {
        city?.let {
            if(it.isNotEmpty()) {
                viewModel.fetchForecastWeather(it)
            }
        }
    }

    private fun showDate(city: String, list: List<com.assignment.model.ListItem>) {
        adapter.submitList(list)
    }
}