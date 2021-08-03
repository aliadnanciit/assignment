package com.weather.view.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.weather.R
import com.weather.common.DensityConverter
import com.weather.common.ItemHorizontalSpaceDecoration
import com.weather.common.ItemVerticalSpaceDecoration
import com.weather.databinding.FragmentForecastWeatherDetailBinding
import com.weather.model.ListItem
import com.weather.model.server.WeatherStates
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
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
            loadForecstWeatherData(selectedCity)
        }
        adapter = ForecastWeatherListAdapter()

        binding.campaignsRecycler.adapter = adapter
        binding.campaignsRecycler.addItemDecoration(
            ItemVerticalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.weather_list_item_vertical_spacing)
                )
            )
        )
        binding.campaignsRecycler.addItemDecoration(
            ItemHorizontalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.weather_list_item_horizontal_spacing)
                )
            )
        )
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

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, Observer {
            it.list?.let { list ->
                showDate(list)
            }
        })

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.weatherStateFlow.collect { campaigns ->
//                    processViewState(campaigns)
//                }
//            }
//        }
        loadForecstWeatherData(selectedCity)
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
        binding.campaignsRecycler.visibility = View.GONE
        when (weatherState) {
            is WeatherStates.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            is WeatherStates.NoContent -> {
                binding.noContent.visibility = View.VISIBLE
            }
            is WeatherStates.Success -> {
                binding.campaignsRecycler.visibility = View.VISIBLE
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
            loadForecstWeatherData(cities[position])
        }
    }

    private fun loadForecstWeatherData(city: String?) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        city?.let {
            viewModel.fetchForecastWeather(it)
        } ?:   viewModel.fetchForecastWeather(defaultCity)
    }

    private fun showDate(list: List<ListItem>) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.noContent.visibility = View.GONE
        binding.campaignsRecycler.visibility = View.GONE

        binding.campaignsRecycler.visibility = View.VISIBLE
        adapter.submitList(list)
    }
}