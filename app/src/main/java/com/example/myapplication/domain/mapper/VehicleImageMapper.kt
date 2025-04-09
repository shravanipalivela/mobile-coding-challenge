package com.example.myapplication.domain.mapper

import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.domain.model.VehicleImageDomainModel
import com.example.myapplication.domain.utils.appendQueryParam
import com.example.myapplication.domain.utils.isValidUri

fun VehicleDto.toVehicleImageDomainModel(): List<VehicleImageDomainModel> {

    return this.images?.mapNotNull { image ->
        image.uri.takeIf { it.isNotBlank() }?.let { rawUri ->
            val baseUrl = "https://$rawUri"
            val id = rawUri.substringAfterLast('/').substringBefore('?')
            val thumbnailUrl = appendQueryParam(baseUrl, "rule", "mo-640.jpg")
            val highResUrl = appendQueryParam(baseUrl, "rule", "mo-1600.jpg")
            VehicleImageDomainModel(
                imageId = id,
                thumbnailUrl = thumbnailUrl,
                highResUrl = highResUrl
            )
        }
    } ?: emptyList()
}