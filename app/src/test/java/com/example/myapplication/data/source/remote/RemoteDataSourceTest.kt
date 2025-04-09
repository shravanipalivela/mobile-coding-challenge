package com.example.myapplication.data.source.remote

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.model.VehicleImage
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RemoteDataSourceTest {

    private lateinit var apiService: ApiService
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        apiService = mockk()
        remoteDataSource = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `fetchVehicle should return success when API call is successful`() = runBlocking {
        // Given
        val expectedDto = VehicleDto(
            id = 285041801,
            images = listOf(
                VehicleImage(uri = "https://images.mobile.de/vehicle1.jpg"),
                VehicleImage(uri = "https://images.mobile.de/vehicle2.jpg")
            )
        )
        coEvery { apiService.getVehicle("285041801") } returns expectedDto

        // When
        val result = remoteDataSource.fetchVehicle()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedDto, result.getOrNull())
    }

    @Test
    fun `fetchVehicle should return failure on HttpException`() = runBlocking {
        // Given
        val httpException = HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))
        coEvery { apiService.getVehicle("285041801") } throws (httpException)

        // When
        val result = remoteDataSource.fetchVehicle()

        // Then
        assertTrue(result.isFailure)
        assertEquals("HTTP error: 404 - ", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchVehicle should return failure on IOException`() = runTest {
        // Given
        coEvery { apiService.getVehicle("285041801") } throws (IOException("Network failure"))

        // When
        val result = remoteDataSource.fetchVehicle()

        // Then
        assertTrue(result.isFailure)
        assertEquals(
            "No internet connection or network error: Network failure",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun `fetchVehicle should return failure on unexpected Exception`() = runTest {
        // Given
        coEvery { apiService.getVehicle("285041801") } throws (RuntimeException("Unexpected crash"))

        // When
        val result = remoteDataSource.fetchVehicle()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Unexpected error: Unexpected crash", result.exceptionOrNull()?.message)
    }

}