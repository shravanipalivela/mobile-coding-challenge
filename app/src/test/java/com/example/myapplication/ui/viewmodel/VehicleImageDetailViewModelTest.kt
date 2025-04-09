package com.example.myapplication.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.domain.model.VehicleImageDomainModel
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import com.example.myapplication.ui.screens.vehicle.appearance.detail.VehicleImageDetailUiState
import com.example.myapplication.ui.screens.vehicle.appearance.detail.VehicleImageDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class VehicleImageDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: VehicleImageDetailViewModel
    private var vehicleGalleryUseCase: VehicleGalleryUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = VehicleImageDetailViewModel(vehicleGalleryUseCase)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher to the original one
        Dispatchers.resetMain()
    }


    @Test
    fun `loadVehicleImage should post Success when image with highResUrl is found`(): Unit = runTest {
        // Given
        val id = "image123"
        val domainModel = VehicleImageDomainModel(
            imageId = id,
            thumbnailUrl = "https://example.com/thumb.jpg",
            highResUrl = "https://example.com/highres.jpg"
        )
        coEvery { vehicleGalleryUseCase.getVehicleGalleryImages() } returns listOf(domainModel)

        // When
        viewModel.loadVehicleImage(id)
        advanceUntilIdle()

        // Then
        val result = viewModel.vehicleImage.value
        assertTrue(result is VehicleImageDetailUiState.Success)
        assertEquals(domainModel.highResUrl, (result as VehicleImageDetailUiState.Success).imageUrl)
    }

    @Test
    fun `loadVehicleImage should post NoHighResImage when image is found but highResUrl is empty`() = runTest {
        val id = "image123"
        val domainModel = VehicleImageDomainModel(id, "https://example.com/thumb.jpg", "")
        coEvery { vehicleGalleryUseCase.getVehicleGalleryImages() } returns listOf(domainModel)

        viewModel.loadVehicleImage(id)
        advanceUntilIdle()

        val result = viewModel.vehicleImage.value
        assertTrue(result is VehicleImageDetailUiState.NoHighResImage)
    }

    @Test
    fun `loadVehicleImage should post Error when image is not found`() = runTest {
        coEvery { vehicleGalleryUseCase.getVehicleGalleryImages() } returns emptyList()

        viewModel.loadVehicleImage("non-existent-id")
        advanceUntilIdle()

        val result = viewModel.vehicleImage.value
        assertTrue(result is VehicleImageDetailUiState.Error)
        assertEquals("Image not found", (result as VehicleImageDetailUiState.Error).errorMessage)
    }

    @Test
    fun `loadVehicleImage should post Error on exception`() = runTest {
        coEvery { vehicleGalleryUseCase.getVehicleGalleryImages() } throws RuntimeException("Something went wrong")

        viewModel.loadVehicleImage("any-id")
        advanceUntilIdle()

        val result = viewModel.vehicleImage.value
        assertTrue(result is VehicleImageDetailUiState.Error)
        assertEquals("No internet connection. Please check your connection and try again", (result as VehicleImageDetailUiState.Error).errorMessage)
    }
}
