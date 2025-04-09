package com.example.myapplication.integrationtests

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.repository.VehicleRepositoryImpl
import com.example.myapplication.data.source.remote.RemoteDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VehicleRepositoryApiServiceIntegrationTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var repository: VehicleRepositoryImpl

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

        repository = VehicleRepositoryImpl(remoteDataSource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `loadVehicle returns VehicleDto on success`() = runTest {
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
        val result = repository.loadVehicle()

        // Then
        assertTrue(result.isSuccess)
        val vehicle = result.getOrThrow()
        assertEquals(vehicle.title,"Test Car")
        assertEquals(vehicle.images?.get(0)?.uri,"img.classistatic.de/api/v1/mo-prod/images/test-image-id")

    }

    @Test
    fun `loadVehicle returns failure on HTTP error`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        // When
        val result = repository.loadVehicle()

        // Then
        assertTrue(result.isFailure)


    }
}