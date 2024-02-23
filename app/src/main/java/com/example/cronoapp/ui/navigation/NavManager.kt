package com.example.cronoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import com.example.cronoapp.ui.chronometer.AddView
import com.example.cronoapp.ui.chronometer.EditView
import com.example.cronoapp.ui.chronometer.HomeView
import com.example.cronoapp.ui.onBoarding.MainOnBoarding
import com.example.cronoapp.ui.splashScreen.SplashScreen
import com.example.cronoapp.ui.viewModels.AddChronometerViewModel
import com.example.cronoapp.ui.viewModels.ChronometerViewModel
import com.example.cronoapp.ui.viewModels.EditChronometerViewModel

@Composable
fun NavManager(
    addChronometerViewModel: AddChronometerViewModel,
    chronometerViewModel: ChronometerViewModel,
    editChronometerViewModel: EditChronometerViewModel,
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean,
) {
    val navController = rememberNavController()
    val isFirstTime = dataStore.getFirstTime.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination =  if (isFirstTime.value) "splashScreen_view" else "home_view"
    ) {
        composable(route = "home_view") {
            HomeView(
                navController = navController,
                chronometerViewModel =  chronometerViewModel,
                dataStore = dataStore,
                isDarkMode = isDarkMode
            )
        }

        composable(route = "add_view") {
            AddView(
                navController = navController,
                addChronometerViewModel = addChronometerViewModel,
                chronometerViewModel = chronometerViewModel,
                dataStore = dataStore,
                isDarkMode = isDarkMode
            )
        }

        composable(
            route = "edit_view/{id}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.LongType }
            )
        ) {
            val id = it.arguments?.getLong("id") ?: 0
            EditView(
                navController = navController,
                editChronometerViewModel,
                chronometerViewModel = chronometerViewModel,
                id = id,
                dataStore = dataStore,
                isDarkMode = isDarkMode
            )
        }

        composable(route = "splashScreen_view") {
            SplashScreen(
                navigationController = navController,
                isFirstTime = isFirstTime.value
            )
        }

        composable(route = "onBoarding_view") {
            MainOnBoarding(
                navigationController = navController,
                dataStore = dataStore
            )
        }
    }
}