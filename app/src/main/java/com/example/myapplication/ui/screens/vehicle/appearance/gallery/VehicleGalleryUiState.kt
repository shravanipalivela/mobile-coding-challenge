package com.example.myapplication.ui.screens.vehicle.appearance.gallery

import com.example.myapplication.domain.model.VehicleImageDomainModel
import com.example.myapplication.ui.screens.vehicle.appearance.detail.VehicleImageDetailUiState


sealed class VehicleGalleryUiState {
    object Loading : VehicleGalleryUiState() // Images are loading
    data class Success(val images: List<VehicleImageDomainModel>) :
        VehicleGalleryUiState() // Successfully loaded images

    data class Error(val errorMessage: String) : VehicleGalleryUiState() // Error loading images
    object NoImagesFound : VehicleGalleryUiState()
}
