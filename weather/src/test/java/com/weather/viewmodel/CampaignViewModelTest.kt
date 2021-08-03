package com.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.model.Weather
import com.weather.usecase.GetWeatherListUseCase
import io.mockk.impl.annotations.MockK
import org.junit.Rule

class CampaignViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @MockK
    private lateinit var weather: Weather
    private lateinit var viewModel: WeatherViewModel

    @MockK
    private lateinit var getWeatherListUseCase: GetWeatherListUseCase

    /*@Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CampaignViewModel(
            getCampaignListUseCase
        )
    }

    @Test
    fun `should show success state`() {
        coEvery { getCampaignListUseCase.execute() } returns flowOf(listOf(campaign))

        viewModel.getCampaigns()

        Assert.assertEquals(
            CampaignStates.Success(listOf(campaign)),
            viewModel.campaignsStateFlow.value
        )
    }*/

    // NEED to fix this scenario as well
    /*@Test
    fun `should show error state`() {
        val throwable: Throwable = RuntimeException()
        coEvery { getCampaignListUseCase.execute() } throws throwable

        viewModel.getCampaigns()

        Assert.assertEquals(
            CampaignStates.Error(throwable),
            viewModel.campaignsStateFlow.value
        )
    }*/
}