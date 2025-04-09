package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.VehicleImageDomainModel

interface VehicleGalleryUseCase {
    suspend fun getVehicleGalleryImages(): List<VehicleImageDomainModel>
}