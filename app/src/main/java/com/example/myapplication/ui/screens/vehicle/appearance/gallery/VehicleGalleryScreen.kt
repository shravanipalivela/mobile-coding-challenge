package com.example.myapplication.ui.screens.vehicle.appearance.gallery

import android.annotation.SuppressLint
import android.provider.Settings.Global.getString
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.components.EmptyState
import com.example.myapplication.ui.components.ErrorContent
import com.example.myapplication.ui.components.GalleryContent
import com.example.myapplication.ui.components.LoadingIndicator
import com.example.myapplication.ui.theme.MobileDeOrange

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VehicleGalleryScreen(navController: NavHostController) {
    val viewModel: VehicleGalleryViewModel = hiltViewModel()

    val uiState = viewModel.galleryState.collectAsState()

    Scaffold(
        topBar = { AppBar() },
        content = { paddingValues ->
            when (uiState.value) {
                is VehicleGalleryUiState.Loading -> {
                    LoadingIndicator(paddingValues = paddingValues)
                }

                is VehicleGalleryUiState.Success -> {
                    val imageList = (uiState.value as VehicleGalleryUiState.Success).images
                    GalleryContent(navController = navController, imageList , paddingValues = paddingValues)
                }

                is VehicleGalleryUiState.Error -> {
                    val errorMessage = (uiState.value as VehicleGalleryUiState.Error).errorMessage
                    ErrorContent(errorMessage = errorMessage) {
                        viewModel.loadVehicleGallery()

                    }
                }

                is VehicleGalleryUiState.NoImagesFound -> {
                    EmptyState(text = stringResource(id = R.string.empty_state_gallery))

                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.headlineSmall.copy(
               fontWeight = FontWeight.Bold))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MobileDeOrange // Set the background color of the TopAppBar
        )
    )
}

