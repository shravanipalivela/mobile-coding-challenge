package com.example.myapplication.ui.screens.vehicle.appearance.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VehicleGalleryViewModel @Inject constructor(private val vehicleGalleryUseCase: VehicleGalleryUseCase) :
    ViewModel() {

    private val _galleryState =
        MutableStateFlow<VehicleGalleryUiState>(VehicleGalleryUiState.Loading)
    val galleryState: StateFlow<VehicleGalleryUiState> = _galleryState.asStateFlow()

    init {
        loadVehicleGallery()
    }

    fun loadVehicleGallery() {
        _galleryState.update { VehicleGalleryUiState.Loading }
        viewModelScope.launch {
            try {
                val imagesList = vehicleGalleryUseCase.getVehicleGalleryImages()
                if (imagesList.isNotEmpty()) {
                    _galleryState.update { VehicleGalleryUiState.Success(images = imagesList) }
                } else {
                    _galleryState.update { VehicleGalleryUiState.Error("No images found") }
                }

            } catch (e: Exception) {
                _galleryState.update { VehicleGalleryUiState.Error("No internet connection. Please check your connection and try again") }
            }
        }

    }

}