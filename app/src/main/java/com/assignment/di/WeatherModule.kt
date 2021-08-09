package com.assignment.di

import com.assignment.repository.WeatherRepository
import com.assignment.repository.WeatherRepositoryImpl
import com.assignment.service.WeatherApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Binds
    abstract fun bindRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @Provides
        fun provideWeatherApi(
            retrofit: Retrofit
        ): WeatherApi {
            return retrofit.create(WeatherApi::class.java)
        }
    }
}