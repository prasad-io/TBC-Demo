package com.app.tbc.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.tbc.presentation.moviedetail.MovieDetailScreen
import com.app.tbc.presentation.movielist.MovieListingScreen
import com.app.tbc.presentation.search.SearchScreen
import com.app.tbc.presentation.splash.SplashScreen
import com.app.tbc.ui.theme.Background


@Composable
fun NavGraph(startDestination: String = Screen.Splash.route, context: Context) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute != Screen.Splash.route) {
            AppBottomNavigation(navController, context)
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(Screen.Splash.route) {
                    SplashScreen(navController)
                }
                composable(NavigationItem.Home.route) {
                    MovieListingScreen(
                        navigationController = navController, context
                    )
                }
                composable(NavigationItem.Search.route) {
                    SearchScreen()
                }
                composable(
                    route = Screen.MovieDetail.route,
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getInt("movieId")
                    MovieDetailScreen(movieId, navigationController = navController, context)
                }
            }

        }

    }
}