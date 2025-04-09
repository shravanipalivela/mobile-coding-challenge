package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.vehicle.appearance.detail.VehicleImageDetailScreen
import com.example.myapplication.ui.screens.vehicle.appearance.gallery.VehicleGalleryScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
              VehicleApp()
            }
        }
    }
}

@Composable
fun VehicleApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "gallery") {
        composable("gallery") { VehicleGalleryScreen(navController) }
        composable("imageDetail/{id}",arguments = listOf(navArgument("id") { type = NavType.StringType })) {
                backStackEntry ->
            val imageId = backStackEntry.arguments?.getString("id")
            imageId?.let{VehicleImageDetailScreen(it,navController)}

        }

    }


}



