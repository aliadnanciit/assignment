package com.weather.view.list

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.weather.R
import com.weather.databinding.FragmentHomeBinding
import com.weather.model.Weather
import com.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), WeatherClickListener {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : WeatherListAdapter

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


    private fun initViewSetup() {
        binding.searchProxy.setOnClickListener {
            val uri = getString(R.string.deeplink_weather_details).replace("{value}", "true")
            findNavController().navigate(Uri.parse(uri))
        }
    }

    override fun onclick(weather: Weather) {
        TODO("Not yet implemented")
    }
}