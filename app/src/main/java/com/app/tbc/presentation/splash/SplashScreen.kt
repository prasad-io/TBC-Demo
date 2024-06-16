package com.app.tbc.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.app.tbc.navigation.NavigationItem
import com.app.tbc.ui.theme.Background
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(300)
        navController.navigate(NavigationItem.Home.route) {
            popUpTo(0) { inclusive = true }
        }
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = Background),
    )
}
