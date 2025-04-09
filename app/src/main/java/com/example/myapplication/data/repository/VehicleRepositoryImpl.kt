package com.example.myapplication.data.repository

import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.source.remote.RemoteDataSource
import javax.inject.Inject

class VehicleRepositoryImpl@Inject constructor(private val remoteDataSource: RemoteDataSource) : VehicleRepository {
    override suspend fun loadVehicle(): Result<VehicleDto> {
        return remoteDataSource.fetchVehicle()
    }

}