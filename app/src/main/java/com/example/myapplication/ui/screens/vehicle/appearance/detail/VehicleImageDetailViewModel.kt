package com.example.myapplication.ui.screens.vehicle.appearance.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleImageDetailViewModel @Inject constructor(private val vehicleGalleryUseCase: VehicleGalleryUseCase) :
    ViewModel() {
    private val _vehicleImage = MutableLiveData<VehicleImageDetailUiState>()
    val vehicleImage: LiveData<VehicleImageDetailUiState> get() = _vehicleImage

    fun loadVehicleImage(id: String) {
        _vehicleImage.value = VehicleImageDetailUiState.Loading
        viewModelScope.launch {
            try {
                val image = vehicleGalleryUseCase.getVehicleGalleryImages().find { it.imageId == id }
                if (image != null) {
                    if (image.highResUrl.isEmpty()) {
                        _vehicleImage.value = VehicleImageDetailUiState.NoHighResImage
                    } else {
                    _vehicleImage.value =
                        VehicleImageDetailUiState.Success(imageUrl = image.highResUrl)}
                } else {
                    _vehicleImage.value = VehicleImageDetailUiState.Error("Image not found")
                }

            } catch (e: Exception) {
                _vehicleImage.value = VehicleImageDetailUiState.Error("No internet connection. Please check your connection and try again")
            }

        }
    }

}