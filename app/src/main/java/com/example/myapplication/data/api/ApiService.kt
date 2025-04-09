package com.example.myapplication.data.api

import com.example.myapplication.data.model.VehicleDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("svc/a/{id}")
    suspend fun getVehicle(
        @Path("id") id: String,
    ): VehicleDto
}