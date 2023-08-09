package com.arturomarmolejo.nycschoolscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturomarmolejo.nycschoolscompose.presentation.screens.DetailsScreen
import com.arturomarmolejo.nycschoolscompose.presentation.screens.HomeScreen
import com.arturomarmolejo.nycschoolscompose.presentation.viewmodel.SchoolsViewModel

@Composable
fun Navigation(
    viewModel: SchoolsViewModel
) {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = Routes.HomeScreen.route ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(schoolsViewModel = viewModel, navController = navigationController)
        }
        composable(Routes.DetailsScreen.route) {
            DetailsScreen(viewModel)
        }
    }
}