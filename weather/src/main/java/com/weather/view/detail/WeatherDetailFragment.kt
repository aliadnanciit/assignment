package com.weather.view.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.weather.R
import com.weather.common.TemperatureUtil
import com.weather.databinding.FragmentForecastWeatherDetailBinding
import com.weather.model.ListItem
import com.weather.model.server.WeatherStates
import com.weather.viewmodel.WeatherSettingViewModel
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private val weatherSettingViewModel: WeatherSettingViewModel by activityViewModels()

    @Inject
    lateinit var temperatureUtil: TemperatureUtil

    private lateinit var cities : Array<String>
    private var selectedCity: String? = null
    private val defaultCity = "Dubai"


    private lateinit var binding: FragmentForecastWeatherDetailBinding
    private lateinit var adapter: ForecastWeatherListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastWeatherDetailBinding.inflate(layoutInflater)

        binding.retry.setOnClickListener {
            loadForecastWeatherData(selectedCity)
        }
        adapter = ForecastWeatherListAdapter(
            temperatureUtil,
            weatherSettingViewModel.showTempInDegree.value!!
        )

        binding.weatherRecyclerView.adapter = adapter
        binding.addAsFavCity.setOnClickListener {
            if(binding.cityName.text.toString().isNotEmpty()) {
                viewModel.addFav(binding.cityName.text.toString())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            if(it.getString("city") != "none") {
                selectedCity = it.getString("city")!!
            }
        }

        if (showSearchBar()) {
            cities = requireActivity().resources.getStringArray(R.array.cities)
            initViewSetup()
        } else {
            binding.autoCompleteSearchView.visibility = View.GONE
        }
        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, Observer { forecastWeather ->
            forecastWeather.list?.let { list ->
                showDate(forecastWeather.city.name, list)
            }
        })
        loadForecastWeatherData(selectedCity)
    }

    private fun showSearchBar(): Boolean {
        arguments?.let {
            return it.getString("value")?.toBoolean() ?: false
        }
        return false
    }

    private fun processViewState(weatherState: WeatherStates) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.noContent.visibility = View.GONE
        binding.weatherRecyclerView.visibility = View.GONE
        when (weatherState) {
            is WeatherStates.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            is WeatherStates.NoContent -> {
                binding.noContent.visibility = View.VISIBLE
            }
            is WeatherStates.Success -> {
                binding.weatherRecyclerView.visibility = View.VISIBLE
//                showDate(weatherState.list)
            }
            is WeatherStates.Error -> {
                binding.errorContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewSetup() {
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.select_dialog_item, cities)
        binding.autoCompleteSearchView.threshold = 1
        binding.autoCompleteSearchView.setAdapter(adapter)
        binding.autoCompleteSearchView.setTextColor(Color.RED)

        binding.autoCompleteSearchView.setOnItemClickListener { parent, view, position, id ->
            loadForecastWeatherData(cities[position])
        }
    }

    private fun loadForecastWeatherData(city: String?) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        city?.let {
            viewModel.fetchForecastWeather(it)
        } ?:   viewModel.fetchForecastWeather(defaultCity)
    }

    private fun showDate(city: String, list: List<ListItem>) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.noContent.visibility = View.GONE
        binding.weatherRecyclerView.visibility = View.GONE

        binding.cityInfoContainer.visibility = View.VISIBLE
        binding.cityName.text = ""

        binding.weatherRecyclerView.visibility = View.VISIBLE
        adapter.submitList(list)
    }
}