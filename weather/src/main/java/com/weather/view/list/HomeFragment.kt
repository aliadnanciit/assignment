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
import com.weather.common.DensityConverter
import com.weather.common.ItemHorizontalSpaceDecoration
import com.weather.common.ItemVerticalSpaceDecoration
import com.weather.databinding.FragmentHomeBinding
import com.weather.model.WeatherResponseData
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(), CityOnclickListener {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : FavouriteCitiesWeatherAdapter

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
        viewModel.fetchForecastWeather("dubai")

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

        viewModel.favouritesLiveData.observe(viewLifecycleOwner, {
            showFavCitiesList(it.toList())
        })

        viewModel.fetchFavourites()
//        viewModel.addFav("Berlin")
    }

    private fun initViewSetup() {
        binding.searchProxy.setOnClickListener {
            val uri = getString(R.string.deeplink_weather_details)
                .replace("{value}", "true")
                .replace("{city}", "none")
            findNavController().navigate(Uri.parse(uri))
        }

        viewModel.weatherByLocation.observe(viewLifecycleOwner, Observer {
            updateUserLocationCard(it)
        })

        adapter = FavouriteCitiesWeatherAdapter(this)

        binding.favCitiesRecyclerView.adapter = adapter
        binding.favCitiesRecyclerView.addItemDecoration(
            ItemVerticalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.weather_list_item_vertical_spacing)
                )
            )
        )
        binding.favCitiesRecyclerView.addItemDecoration(
            ItemHorizontalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.weather_list_item_horizontal_spacing)
                )
            )
        )

        loadCampaignsData()
    }

    private fun updateUserLocationCard(response: WeatherResponseData) {
        binding.userLocationCard.visibility = View.VISIBLE
        binding.name.text = response.name
        binding.humidity.text = response.main.humidity.toString()
        binding.temperature.text = response.main.temp.toString()
        binding.wind.text = response.wind.speed.toString()
    }

    override fun onClick(city: String) {
        Timber.d(city)
        val uri = getString(R.string.deeplink_weather_details)
            .replace("{value}", "false")
            .replace("{city}", city)
        findNavController().navigate(Uri.parse(uri))
    }

    private fun showFavCitiesList(list: List<String>) {
        adapter.submitList(list)
    }
}