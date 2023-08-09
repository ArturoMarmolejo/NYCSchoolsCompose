package com.arturomarmolejo.nycschoolscompose.presentation.navigation

sealed class Routes(val route: String) {
    object HomeScreen: Routes("HomeScreen")
    object DetailsScreen: Routes("DetailsScreen")
}
