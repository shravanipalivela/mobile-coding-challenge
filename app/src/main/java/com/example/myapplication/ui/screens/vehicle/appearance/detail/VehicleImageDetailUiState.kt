package com.example.myapplication.ui.screens.vehicle.appearance.detail


sealed class VehicleImageDetailUiState {
    object Loading : VehicleImageDetailUiState() // Image is loading
    data class Success(val imageUrl: String) :
        VehicleImageDetailUiState() // Successfully loaded image
    data class Error(val errorMessage: String) : VehicleImageDetailUiState() // Error loading image
    object NoHighResImage : VehicleImageDetailUiState()
}