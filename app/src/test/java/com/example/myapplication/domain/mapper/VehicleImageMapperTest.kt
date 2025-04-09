package com.example.myapplication.domain.mapper

import com.example.myapplication.data.model.VehicleDto
import com.example.myapplication.data.model.VehicleImage
import org.junit.Test
import org.junit.Assert.*

class VehicleImageMapperTest {

    @Test
    fun `test toVehicleImageDomainModel should map DTO to domain model correctly`() {
        val vehicleDto = VehicleDto(
           id = 285041801,
            images = listOf(
                VehicleImage(uri = "img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d")
            )
        )

        val domainModel = vehicleDto.toVehicleImageDomainModel()

        assertEquals(1, domainModel.size)
        assertEquals("3caae5b2-ef50-4bbd-945b-563f7237a04d", domainModel[0].imageId)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-640.jpg", domainModel[0].thumbnailUrl)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-1600.jpg", domainModel[0].highResUrl)
    }

    //
    @Test
    fun `test toVehicleImageDomainModel should map DTO to domain model correctly when the image list is more than 1`() {
        val vehicleDto = VehicleDto(
            id = 285041801,
            images = listOf(
                VehicleImage(uri = "img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d"),
                VehicleImage(uri = "img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04f")
            )
        )

        val domainModel = vehicleDto.toVehicleImageDomainModel()

        assertEquals(2, domainModel.size)
        assertEquals("3caae5b2-ef50-4bbd-945b-563f7237a04d", domainModel[0].imageId)
        assertEquals("3caae5b2-ef50-4bbd-945b-563f7237a04f", domainModel[1].imageId)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-640.jpg", domainModel[0].thumbnailUrl)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04d?rule=mo-1600.jpg", domainModel[0].highResUrl)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04f?rule=mo-640.jpg", domainModel[1].thumbnailUrl)
        assertEquals("https://img.classistatic.de/api/v1/mo-prod/images/3caae5b2-ef50-4bbd-945b-563f7237a04f?rule=mo-1600.jpg", domainModel[1].highResUrl)}

    // Test case when the images list is empty
    @Test
    fun `test toVehicleImageDomainModel should return empty list when images is null or empty`() {
        val vehicleDtoEmpty = VehicleDto(
            id = 285041801,
            images = emptyList()
        )
        val vehicleDtoNull = VehicleDto(
           id = 285041802,
            images = null
        )

        val resultEmpty = vehicleDtoEmpty.toVehicleImageDomainModel()
        val resultNull = vehicleDtoNull.toVehicleImageDomainModel()

        assertTrue(resultEmpty.isEmpty())
        assertTrue(resultNull.isEmpty())
    }
}