package com.weather.view.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weather.R
import com.weather.common.DensityConverter
import com.weather.common.ItemHorizontalSpaceDecoration
import com.weather.common.ItemVerticalSpaceDecoration
import com.weather.databinding.ActivityWeatherListBinding
import com.weather.model.Weather
import com.weather.model.server.WeatherStates
import com.weather.view.detail.WeatherDetailActivity
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Deprecated(message = "Use HomeFragment inplace of this")
@AndroidEntryPoint
class WeatherListActivity : AppCompatActivity(), WeatherClickListener {

    private val viewModel: WeatherViewModel by viewModels()

    lateinit var binding: ActivityWeatherListBinding
    private lateinit var adapter : WeatherListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.retry.setOnClickListener {
            loadWeatherData()
        }
        adapter = WeatherListAdapter(this)

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


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherStateFlow.collect { campaigns ->
                    processViewState(campaigns)
                }
            }
        }
        loadWeatherData()
    }

    private fun processViewState(weatherState: WeatherStates) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.noContent.visibility = View.GONE
        binding.campaignsRecycler.visibility = View.GONE
        when(weatherState) {
            is WeatherStates.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            is WeatherStates.NoContent -> {
                binding.noContent.visibility = View.VISIBLE
            }
            is WeatherStates.Success -> {
                binding.campaignsRecycler.visibility = View.VISIBLE
                showDate(weatherState.list)
            }
            is WeatherStates.Error -> {
                binding.errorContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun loadWeatherData() {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        viewModel.fetchCampaigns()
    }

    private fun showDate(list: List<Weather>) {
        adapter.submitList(list)
    }

    override fun onclick(weather: Weather) {
        WeatherDetailActivity.startDetailActivity(this, weather)
    }
}