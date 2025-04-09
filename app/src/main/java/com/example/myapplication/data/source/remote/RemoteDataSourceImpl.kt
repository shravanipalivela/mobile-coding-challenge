package com.example.myapplication.data.source.remote

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.VehicleDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun fetchVehicle(): Result<VehicleDto> {
        return try {
            val response = apiService.getVehicle("285041801") //419308792 with many images
            Result.success(response)
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            Result.failure(Exception("HTTP error: ${e.code()} - $errorMessage"))
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection or network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.localizedMessage}"))
        }
    }
}

