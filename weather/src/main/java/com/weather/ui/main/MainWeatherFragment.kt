package com.weather.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.R
import com.weather.common.TemperatureUtil
import com.weather.databinding.FragmentMainWeatherBinding
import com.weather.model.FavCityWeatherState
import com.weather.model.UserWeatherState
import com.weather.model.WeatherResponseData
import com.weather.viewmodel.FavCityWeatherViewModel
import com.weather.viewmodel.WeatherSettingViewModel
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

const val PERMISSIONS_REQUEST_ACCESS_LOCATION: Int = 123

@AndroidEntryPoint
class MainWeatherFragment : Fragment(), CityOnclickListener {

    private val viewModel: WeatherViewModel by viewModels()
    private val favCityWeatherViewModel: FavCityWeatherViewModel by viewModels()
    private val weatherSettingViewModel: WeatherSettingViewModel by activityViewModels()

    private lateinit var binding: FragmentMainWeatherBinding
    private lateinit var adapter: FavouriteCitiesWeatherAdapter

    @Inject
    lateinit var temperatureUtil: TemperatureUtil

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewSetup()
    }

    private fun loadData() {
        viewModel.fetchForecastWeather("dubai")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        favCityWeatherViewModel.favouritesLiveData.observe(viewLifecycleOwner, {
            it?.let { favCityWeatherState ->
                binding.favCitiesRecyclerView.visibility = View.GONE
                binding.addFavCityContainer.visibility = View.GONE

                when (favCityWeatherState) {
                    is FavCityWeatherState.Success -> {
                        binding.favCitiesRecyclerView.visibility = View.VISIBLE
                        showFavCitiesList(favCityWeatherState.results.toList())
                    }
                    is FavCityWeatherState.NoFavList -> {
                        binding.addFavCityContainer.visibility = View.VISIBLE
                    }
                }
            }
        })

        favCityWeatherViewModel.fetch()

        if (
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showPermissionDialog()
            return
        }
        findLocation()
    }

    @SuppressLint("MissingPermission")
    private fun findLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.fetchWeatherByLocation(
                        it.latitude.toString(),
                        it.longitude.toString(),
                        "metric"
                    )
                }
            }
    }

    private fun initViewSetup() {
        binding.searchProxy.setOnClickListener {
            showSearchFavCity("none", true)
        }

        viewModel.weatherByLocation.observe(viewLifecycleOwner, Observer {
            updateTodayWeatherView(it)
        })

        weatherSettingViewModel.showTempInDegree.observe(viewLifecycleOwner, Observer {
            binding.tempDegree.isChecked = it
            updateTodayWeatherView(viewModel.weatherByLocation.value)
        })

        adapter = FavouriteCitiesWeatherAdapter(this)
        binding.favCitiesRecyclerView.adapter = adapter
        loadData()

        binding.tempDegree.setOnClickListener {
            weatherSettingViewModel.toggleSelection()
        }
        binding.tempDegree.isChecked = weatherSettingViewModel.showTempInDegree.value!!
        updateTodayWeatherView(viewModel.weatherByLocation.value)

        binding.permissionButton.setOnClickListener {
            showPermissionDialog()
        }

        binding.addFavCityButton.setOnClickListener {
            showSearchFavCity("none", true)
        }
    }

    private fun updateTodayWeatherView(it: UserWeatherState<WeatherResponseData>?) {
        it?.let { userWeatherState ->
            binding.locationBasedWeather.visibility = View.GONE
            binding.userWeatherLoading.visibility = View.GONE
            binding.userWeatherPermission.visibility = View.GONE
            when (userWeatherState) {
                is UserWeatherState.Success -> {
                    updateUserLocationCard(userWeatherState.results)
                    viewModel.scheduleNotification(userWeatherState.results.name)
                }
                is UserWeatherState.Error -> {

                }
                is UserWeatherState.Loading -> {
                    binding.userWeatherLoading.visibility = View.VISIBLE
                }
                is UserWeatherState.PermissionRequired -> {
                    binding.userWeatherPermission.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateUserLocationCard(response: WeatherResponseData) {
        binding.locationBasedWeather.visibility = View.VISIBLE
        binding.name.text = response.name
        binding.humidity.text = response.main.humidity.toString()
        binding.temperature.text = if(weatherSettingViewModel.showTempInDegree.value!!) {
            temperatureUtil.convertToDegree(response.main.temp)
        } else {
            temperatureUtil.convertToFarenheit(response.main.temp)
        }
        binding.wind.text = response.wind.speed.toString()
    }

    override fun onClick(city: String) {
        Timber.d(city)
        showSearchFavCity(city, false)
    }

    private fun showSearchFavCity(city: String, showSearch: Boolean) {
        val uri = getString(R.string.deeplink_weather_details)
            .replace("{value}", "$showSearch")
            .replace("{city}", city)
        findNavController().navigate(Uri.parse(uri))
    }

    private fun showFavCitiesList(list: List<String>) {
        adapter.submitList(list)
    }

    private fun showPermissionDialog() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty()
                && grantResults.first() == PackageManager.PERMISSION_GRANTED
            ) {
                findLocation()
            } else {
                viewModel.needLocationPermission()
            }
        }
    }
}