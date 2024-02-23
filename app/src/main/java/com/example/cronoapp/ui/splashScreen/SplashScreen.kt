package com.example.cronoapp.ui.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.cronoapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigationController: NavController,
    isFirstTime: Boolean
) {
    var screen by remember { mutableStateOf("") }
    screen = if(isFirstTime) "onBoarding_view" else "home_view"
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LaunchedEffect(key1 = true) {
            delay(2000)
            navigationController.navigate(screen) {
                popUpTo(0)
            }
        }

        Image(painter = painterResource(id = R.drawable.chronometer), contentDescription = "Logo")
    }
}