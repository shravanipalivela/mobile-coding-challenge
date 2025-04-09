package com.example.myapplication.ui.screens.vehicle.appearance.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.EmptyState
import com.example.myapplication.ui.components.ErrorContent
import com.example.myapplication.ui.components.ImageDetailContent
import com.example.myapplication.ui.components.LoadingIndicator
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryUiState
import com.example.myapplication.ui.theme.MobileDeOrange

@Composable
fun VehicleImageDetailScreen(id: String, navController: NavController) {
    val viewModel: VehicleImageDetailViewModel = hiltViewModel()

    val uiState = viewModel.vehicleImage.observeAsState()

    LaunchedEffect(id) {
        viewModel.loadVehicleImage(id) // Fetch image details by imageId
    }

    Scaffold(
        topBar = { AppBar(navController) },
        content = { paddingValues ->
            when (uiState.value) {
                is VehicleImageDetailUiState.Loading -> {
                    LoadingIndicator(paddingValues)
                }

                is VehicleImageDetailUiState.Error -> {
                    val errorMessage = (uiState.value as VehicleImageDetailUiState.Error).errorMessage
                    ErrorContent(errorMessage = errorMessage) {
                        viewModel.loadVehicleImage(id)
                    }
                }
                is VehicleImageDetailUiState.Success -> {
                    val imageUrl = (uiState.value as VehicleImageDetailUiState.Success).imageUrl
                    ImageDetailContent(imageUrl)
                }

                is VehicleImageDetailUiState.NoHighResImage -> {
                    EmptyState(text = stringResource(id = R.string.empty_state_detail))
                }

                null ->  {
                    LoadingIndicator(paddingValues)
                }
            }

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.detail_screen_name), style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Gallery"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MobileDeOrange // Set the background color of the TopAppBar
        )
    )
}


