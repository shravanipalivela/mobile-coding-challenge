package com.example.myapplication.data.model

data class VehicleDto(
    val title: String,
    val price: Price?,
    val images: List<VehicleImage>?
)

data class VehicleImage(
    val uri: String
)

data class Price(
    val grs: GrossPrice?
)

data class GrossPrice(
    val amount: Double?,
    val currency: String?,
    val localized: String?
)


