package com.example.myapplication.data.model

data class VehicleDto(
    val id: Long,  //listing_id of the vehicle whose images we want to display
    val images: List<VehicleImage>?
)

data class VehicleImage(
    val uri: String
)


