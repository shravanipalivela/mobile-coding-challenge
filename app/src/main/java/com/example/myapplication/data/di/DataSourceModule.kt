package com.example.myapplication.data.di

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.repository.VehicleRepository
import com.example.myapplication.data.repository.VehicleRepositoryImpl
import com.example.myapplication.data.source.remote.RemoteDataSource
import com.example.myapplication.data.source.remote.RemoteDataSourceImpl
import com.example.myapplication.domain.usecase.VehicleGalleryUseCase
import com.example.myapplication.domain.usecase.VehicleGalleryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideVehicleRepository(remoteDataSource: RemoteDataSource): VehicleRepository {
        return VehicleRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideVehicleGalleryUseCase(vehicleRepository: VehicleRepository): VehicleGalleryUseCase {
        return VehicleGalleryUseCaseImpl(vehicleRepository)
    }

    @Provides
    internal fun provideApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Mobile-Api-Version", "10")
                    .addHeader("User-Agent", "PostmanRuntime/7.32.3") // mimic Postman
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://m.mobile.de/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    }


}