package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageDetailContent(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp) // Add padding on top and bottom
    ) {
        // Image to be displayed, it will fill the available space with padding
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "High-res image",
            modifier = Modifier
                .fillMaxSize() // Make the image fill the available space
                .align(Alignment.Center) // Center the image within the Box
        )
    }
}
