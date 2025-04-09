package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.VehicleRepository
import com.example.myapplication.domain.mapper.toVehicleImageDomainModel
import com.example.myapplication.domain.model.VehicleImageDomainModel
import javax.inject.Inject

class VehicleGalleryUseCaseImpl @Inject constructor(private val vehicleRepository: VehicleRepository) :
    VehicleGalleryUseCase {
    override suspend fun getVehicleGalleryImages(): List<VehicleImageDomainModel> {

        val result = vehicleRepository.loadVehicle()
        val vehicleDto = result.getOrThrow()
        return vehicleDto.toVehicleImageDomainModel()
    }
}