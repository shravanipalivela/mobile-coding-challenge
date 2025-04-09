package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.VehicleRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.model.VehicleImage
import com.example.myapplication.domain.model.VehicleImageDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking

class VehicleGalleryUseCaseTest {

    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var vehicleGalleryUseCase: VehicleGalleryUseCaseImpl

    @Before
    fun setUp() {
        vehicleRepository = mockk()
        vehicleGalleryUseCase = VehicleGalleryUseCaseImpl(vehicleRepository)
    }

    // Test for successful data retrieval
    @Test
    fun `test getVehicleGalleryImages should return transformed data successfully`(): Unit = runBlocking {
        // Given
        val vehicleDto = VehicleDto(
            title = "Test Vehicle",
            price = null,
            images = listOf(VehicleImage(uri = "img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d"))
        )
        val expectedDomainModel = listOf(
            VehicleImageDomainModel(
                imageId = "3caae5b2-ef50-4bbd-945b-563f7237a04d",
                thumbnailUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-640.jpg",
                highResUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-1600.jpg"
            )
        )
        coEvery { vehicleRepository.loadVehicle() } returns Result.success(vehicleDto)

        // When
        val result = vehicleGalleryUseCase.getVehicleGalleryImages()

        // Then
        assertEquals(expectedDomainModel, result)
        coVerify { vehicleRepository.loadVehicle() }
    }

    // Test for repository failure (e.g., network error or unexpected issue)
    @Test
    fun `test getVehicleGalleryImages should handle repository failure gracefully`() = runBlocking {
        // Given
        coEvery { vehicleRepository.loadVehicle() } returns Result.failure(Exception("Network error"))

        // When
        try {
            vehicleGalleryUseCase.getVehicleGalleryImages()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            // Then
            assertEquals("Network error", e.message)
        }

        coVerify { vehicleRepository.loadVehicle() }
    }

    // Test for empty vehicle data
    @Test
    fun `test getVehicleGalleryImages should return empty list for empty vehicle images`() = runBlocking {
        // Given
        val emptyVehicleDto = VehicleDto(
            title = "Test Vehicle",
            price = null,
            images = emptyList()
        )
        coEvery { vehicleRepository.loadVehicle() } returns Result.success(emptyVehicleDto)

        // When
        val result = vehicleGalleryUseCase.getVehicleGalleryImages()

        // Then
        assertTrue(result.isEmpty())
        coVerify { vehicleRepository.loadVehicle() }
    }


}