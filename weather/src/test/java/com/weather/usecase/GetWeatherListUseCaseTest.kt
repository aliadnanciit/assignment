package com.weather.usecase

import com.weather.model.server.WeatherDto
import com.weather.model.server.WeatherMetadata
import com.weather.model.server.WeatherResponse
import com.weather.model.server.ImageDto
import com.weather.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before

private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val IMAGE_URL = "url"
private const val EMPTY = ""

class GetWeatherListUseCaseTest {

    @MockK
    private lateinit var imageDto: ImageDto

    @MockK
    private lateinit var weatherDto: WeatherDto

    @MockK
    private lateinit var metaData: WeatherMetadata

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherListUseCase: GetWeatherListUseCase

    @MockK
    private lateinit var weatherResponse: WeatherResponse

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
//        getCampaignListUseCase = GetCampaignListUseCase(campaignRepository, testCoroutineDispatcher)
    }

    /*@ExperimentalTime
    @Test
    fun `when all name and description are not null then map response`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(
                    listOf(Campaign(name = NAME, description = DESCRIPTION, imageUrl = IMAGE_URL))
                    , expectItem()
                )
                expectComplete()
            }
        }

        coVerify {
            campaignRepository.getCampaigns()
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns null
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign name is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns EMPTY
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if description name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns null
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign description is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns EMPTY
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlocking {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }*/
}