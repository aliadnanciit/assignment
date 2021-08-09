package com.assignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.assignment.databinding.FragmentForecastWeatherDetailBinding
import com.assignment.viewmodel.FavCityWeatherViewModel
import com.assignment.viewmodel.WeatherSettingViewModel
import com.assignment.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    @Inject
    lateinit var temperatureUtil: com.assignment.common.TemperatureUtil

    private val viewModel: WeatherViewModel by viewModels()
    private val faveCityWeatherViewModel: FavCityWeatherViewModel by viewModels()
    private val weatherSettingViewModel: WeatherSettingViewModel by activityViewModels()

    private var paramCity: String? = null
    private lateinit var binding: FragmentForecastWeatherDetailBinding
    private lateinit var adapter: ForecastWeatherListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastWeatherDetailBinding.inflate(layoutInflater)

        binding.searchCityWeather.setOnClickListener {
            loadForecastWeatherData(binding.autoCompleteSearchView.text.toString())
        }
        adapter = ForecastWeatherListAdapter(
            temperatureUtil,
            weatherSettingViewModel.showTempInDegree.value!!
        )

        binding.weatherRecyclerView.adapter = adapter
        binding.addAsFavCity.setOnClickListener {
            if(binding.cityName.text.toString().isNotEmpty()) {
                faveCityWeatherViewModel.add(binding.cityName.text.toString())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if(it.getString("city") != "none") {
                paramCity = it.getString("city")!!
            }
        }

        val visibility = if (paramCity == null) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
        binding.searchPanel.visibility = visibility

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, Observer { forecastWeather ->
            forecastWeather.list?.let { list ->
                showDate(forecastWeather.city.name, list)
            }
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
        binding.cityName.text = city
        binding.weatherRecyclerView.visibility = View.VISIBLE
        binding.cityInfoContainer.visibility = View.VISIBLE
        if(paramCity == null) {
            binding.addAsFavCity.isEnabled = true
        }
        adapter.submitList(list)
    }
}