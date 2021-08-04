package com.weather.repository

//import app.cash.turbine.test
//import com.weather.database.WeatherDao
//import com.weather.database.WeatherEntity
//import com.weather.model.Weather
//import com.weather.model.WeatherDto
//import com.weather.model.server.WeatherMetadata
//import com.weather.model.server.WeatherResponse
//import com.weather.model.server.ImageDto
//import com.weather.service.WeatherApi
//import io.mockk.MockKAnnotations
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.MockK
//import io.mockk.impl.annotations.RelaxedMockK
//import junit.framework.Assert.assertEquals
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Before
//import org.junit.Test
//import retrofit2.Response
//import kotlin.time.ExperimentalTime

class WeatherRepositoryTest {

//    @MockK
//    private lateinit var imageDto: ImageDto
//
//    @MockK
//    private lateinit var weatherDto: WeatherDto
//
//    @MockK
//    private lateinit var weatherMetadata: WeatherMetadata
//
//    @MockK
//    private lateinit var weatherEntity: WeatherEntity
//
//    private val testCoroutineDispatcher = TestCoroutineDispatcher()
//
//    private lateinit var repository: WeatherRepository
//
//    @MockK
//    private lateinit var campaignResponse: WeatherResponse
//
//    @MockK
//    private lateinit var weatherApi: WeatherApi
//
//    @RelaxedMockK
//    private lateinit var weatherDao: WeatherDao
//
//
//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this)
//        repository = WeatherRepositoryImpl(weatherApi, weatherDao, testCoroutineDispatcher)
//    }
//
//    @ExperimentalTime
//    @Test
//    fun `verify data from local database`() = testCoroutineDispatcher.runBlockingTest {
//        val dbData = flowOf(listOf(weatherEntity))
//
//        coEvery { weatherDao.getAllDistinctUntilChanged() } returns dbData
//        coEvery { weatherEntity.uid } returns 1
//        coEvery { weatherEntity.name } returns "name"
//        coEvery { weatherEntity.description } returns "description"
//        coEvery { weatherEntity.imageUrl } returns "http://imgurl"
//
//        repository.getWeatherList().test {
//            assertEquals(
//                listOf(Weather(
//                    id = 1,
//                    name = "name",
//                    description = "description",
//                    imageUrl = "http://imgurl"
//                )), expectItem()
//            )
//            expectComplete()
//        }
//    }
//
//    @ExperimentalTime
//    @Test
//    fun `verify get campaigns`() = testCoroutineDispatcher.runBlockingTest {
//        coEvery { weatherApi.getCampaigns() } returns Response.success(campaignResponse)
//        coEvery { campaignResponse.metadata} returns weatherMetadata
//        coEvery { weatherMetadata.data} returns listOf(weatherDto)
//        coEvery { weatherDto.name } returns "name"
//        coEvery { weatherDto.description } returns "description"
//        coEvery { weatherDto.image} returns imageDto
//        coEvery { imageDto.url } returns ""
//
////        repository.fetchWeatherList()
//
//        coVerify {
//            weatherApi.getCampaigns()
//        }
//        coVerify {
//            weatherDao.deleteAll()
//        }
//        coVerify {
//            weatherDao.insertAll(any())
//        }
//    }


}