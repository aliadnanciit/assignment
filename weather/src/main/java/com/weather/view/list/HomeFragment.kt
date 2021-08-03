package com.weather.view.list

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.R
import com.weather.databinding.FragmentHomeBinding
import com.weather.model.Weather
import com.weather.model.WeatherResponseData
import com.weather.model.server.WeatherResponse
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(), WeatherClickListener {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : WeatherListAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewSetup()
    }

    private fun loadCampaignsData() {
//        viewModel.fetchCampaigns()
        viewModel.fetchForecastWeather()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                Timber.d("test latitude = ${location?.latitude}, lon = ${location?.longitude}")
                location?.let {
                    viewModel.fetchWeatherByLocation(it.latitude.toString(), it.longitude.toString(), "metric")
                }
            }
    }

    private fun initViewSetup() {
        binding.searchProxy.setOnClickListener {
            val uri = getString(R.string.deeplink_weather_details).replace("{value}", "true")
            findNavController().navigate(Uri.parse(uri))
        }

        viewModel.weatherByLocation.observe(viewLifecycleOwner, Observer {
            updateUserLocationCard(it)
        })

        loadCampaignsData()
    }

    private fun updateUserLocationCard(response: WeatherResponseData) {
        binding.userLocationCard.visibility = View.VISIBLE
        binding.name.text = response.name
        binding.humidity.text = response.main.humidity.toString()
        binding.temperature.text = response.main.temp.toString()
        binding.wind.text = response.wind.speed.toString()
    }

    override fun onclick(weather: Weather) {
        TODO("Not yet implemented")
    }
}