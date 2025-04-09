package com.example.myapplication.data.source.remote

import com.example.myapplication.data.model.VehicleDto

interface RemoteDataSource {
    suspend fun fetchVehicle(): Result<VehicleDto>
}