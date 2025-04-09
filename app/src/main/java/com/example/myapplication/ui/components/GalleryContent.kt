package com.example.myapplication.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.domain.model.VehicleImageDomainModel


@Composable
fun GalleryContent(
    navController: NavHostController,
    vehicleImages: List<VehicleImageDomainModel>,
    paddingValues: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(vehicleImages) { vehicleImage ->
            ImageCard(
                imageUrl = vehicleImage.thumbnailUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                onClick = {
                    navController.navigate("imageDetail/${vehicleImage.imageId}")
                })
            }
        }
    }



