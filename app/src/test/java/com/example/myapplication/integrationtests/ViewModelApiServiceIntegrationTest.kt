/*package com.example.myapplication.integrationtests

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.repository.VehicleRepositoryImpl
import com.example.myapplication.data.source.remote.RemoteDataSource
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import com.example.myapplication.domain.usecase.VehicleGalleryUseCaseImpl
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryUiState
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryViewModel
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelApiServiceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var repository: VehicleRepositoryImpl
    private lateinit var useCase: VehicleGalleryUseCase
    private lateinit var viewModel: VehicleGalleryViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // base URL for MockWebServer
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        val remoteDataSource = object : RemoteDataSource {
            override suspend fun fetchVehicle(): Result<VehicleDto> {
                return try {
                    Result.success(apiService.getVehicle("1234"))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }

        Dispatchers.setMain(testDispatcher)
        repository = VehicleRepositoryImpl(remoteDataSource)
        useCase = VehicleGalleryUseCaseImpl(repository)
        viewModel = VehicleGalleryViewModel(useCase)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }
    @Test
    fun `loadVehicleGallery emits Success when API returns valid response`() = runBlocking {

// Given
        val mockJson = """
            {
                "title": "Test Car",
                "price": {
                    "value": 10000,
                    "currency": "EUR"
                },
                "images": [
                    { "uri": "img.classistatic.de/api/v1/mo-prod/images/test-image-id" }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        // When
        launch{viewModel.loadVehicleGallery()}

        // Then
        val result = viewModel.galleryState.value

        // Then
        assertTrue(result is VehicleGalleryUiState.Success)
        coVerify { useCase.getVehicleGalleryImages() }

    }

    @Test
    fun `loadVehicleGallery emits Error when API fails`() =  runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        launch{viewModel.loadVehicleGallery()}


        // Then
        val result = viewModel.galleryState.value
        assertTrue(result is VehicleGalleryUiState.Error)
    }
}*/
