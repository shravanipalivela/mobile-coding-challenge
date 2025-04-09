package com.example.myapplication.data.repository

import com.example.myapplication.data.model.VehicleDto

interface VehicleRepository {
  suspend fun loadVehicle(): Result<VehicleDto>
}