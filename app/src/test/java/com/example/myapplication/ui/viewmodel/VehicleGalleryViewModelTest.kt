package com.example.myapplication.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.domain.model.VehicleImageDomainModel
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryUiState
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleGalleryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: VehicleGalleryViewModel
    private var vehicleGalleryUseCase: VehicleGalleryUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = VehicleGalleryViewModel(vehicleGalleryUseCase)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher to the original one
        Dispatchers.resetMain()
    }

    @Test
    fun `loadVehicleGallery should emit Success when images are fetched successfully`() = runBlocking {
        // Given
        val expectedDomainModel = listOf(
            VehicleImageDomainModel(
                imageId = "3caae5b2-ef50-4bbd-945b-563f7237a04d",
                thumbnailUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-640.jpg",
                highResUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-1600.jpg"
            ),
                    VehicleImageDomainModel(
                    imageId = "3caae5b2-ef50-4bbd-945b-563f7237a04f",
            thumbnailUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04f?rule=mo-640.jpg",
            highResUrl = "https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04f?rule=mo-1600.jpg"
        )
        )
     coEvery{vehicleGalleryUseCase.getVehicleGalleryImages()} returns (expectedDomainModel)

        // When
        viewModel.loadVehicleGallery()


        // Collect the flow and check its value
        val result = viewModel.galleryState.value

        // Then
        assertTrue(result is VehicleGalleryUiState.Success)
        assertEquals(expectedDomainModel, (result as VehicleGalleryUiState.Success).images)

        coVerify { vehicleGalleryUseCase.getVehicleGalleryImages() }
    }

    @Test
    fun `loadVehicleGallery should emit Error when no images are found`() = runBlocking {
        // Given
        coEvery{vehicleGalleryUseCase.getVehicleGalleryImages()} returns (emptyList())

        // When
        viewModel.loadVehicleGallery()
        // Collect the flow and check its value
        val result = viewModel.galleryState.value

        // Then
        assertTrue(result is VehicleGalleryUiState.Error)
        assertEquals("No images found", (result as VehicleGalleryUiState.Error).errorMessage)
    }

    @Test
    fun `loadVehicleGallery should emit Error when an exception is thrown`() = runBlocking {
        // Given
        coEvery { vehicleGalleryUseCase.getVehicleGalleryImages() } throws Exception("Network error")

        // When
        viewModel.loadVehicleGallery()
        // Collect the flow and check its value
        val result = viewModel.galleryState.value

        // Then
        assertTrue(result is VehicleGalleryUiState.Error)
        assertEquals("No internet connection. Please check your connection and try again", (result as VehicleGalleryUiState.Error).errorMessage)
    }
}