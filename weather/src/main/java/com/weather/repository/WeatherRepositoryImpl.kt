package com.weather.repository

import com.weather.common.safeApiCall
import com.weather.database.WeatherDao
import com.weather.database.WeatherEntity
import com.weather.exception.ApiNetworkException
import com.weather.model.Weather
import com.weather.model.server.WeatherResponse
import com.weather.service.WeatherApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getWeatherList(): Flow<List<Weather>> {
        return weatherDao.getAllDistinctUntilChanged()
            .map {
                it.map { entity ->
                    Weather(
                        id = entity.uid,
                        name = entity.name,
                        description = entity.description,
                        imageUrl = entity.imageUrl
                    )
                }
            }
//            .flatMapConcat {
//                fetchCampaigns() // this call causes infinite loop
//                flowOf(it)
//            }
            .flowOn(ioDispatcher)
    }

    override suspend fun fetchWeatherList() {
        withContext(ioDispatcher) {
            val response = safeApiCall {
                weatherApi.getCampaigns()
            }
            if (response.isSuccessful) {
                val list = filterValidCampaigns(response.body()!!)
                saveCampaigns(list)
            } else {
                throw ApiNetworkException("Fail to get campaigns due to network error")
            }
        }
    }

    private fun saveCampaigns(it: List<Weather>) {
        val list = it.map { campaign ->
            WeatherEntity(
                uid = campaign.id,
                name = campaign.name,
                description = campaign.description,
                imageUrl = campaign.imageUrl
            )
        }
        if (list.isNotEmpty()) {
            weatherDao.deleteAll()
            weatherDao.insertAll(list)
        }
    }

    private fun filterValidCampaigns(it: WeatherResponse): List<Weather> {
        return it.metadata.data.filter { campaignDto ->
            campaignDto.name.isNullOrBlank().not()
                    && campaignDto.description.isNullOrBlank().not()
        }.map { campaignDto ->
            Weather(
                name = campaignDto.name!!,
                description = campaignDto.description!!,
                imageUrl = campaignDto.image.url
            )
        }
    }
}