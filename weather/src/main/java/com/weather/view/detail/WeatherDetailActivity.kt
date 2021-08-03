package com.weather.view.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.weather.databinding.ActivityWeatherDetailBinding
import com.weather.model.Weather

const val ACTIVE_CAMPAIGN = "active_campaign"

class WeatherDetailActivity : AppCompatActivity() {

    companion object {
        fun startDetailActivity(activity: Activity, weather: Weather) {
            val intent = Intent(activity, WeatherDetailActivity::class.java)
            intent.putExtra(ACTIVE_CAMPAIGN, weather)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Weather>(ACTIVE_CAMPAIGN)?.let {
            binding.campaignName.text = it.name
            binding.campaignDescription.text = it.description
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.campaignImage)
        }
    }
}