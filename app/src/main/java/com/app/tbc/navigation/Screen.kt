package com.app.tbc.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val SEARCH = "search"
    const val MOVIE_DETAIL = "movieDetail/{movieId}"
}

sealed class Screen(val route: String) {
    object Splash : Screen(Routes.SPLASH)
    object MovieDetail : Screen(Routes.MOVIE_DETAIL) {
        fun createRoute(movieId: Int) = "movieDetail/$movieId"
    }

}

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem(Routes.HOME, Icons.Default.Home, "Home")
    object Search : NavigationItem(Routes.SEARCH, Icons.Default.Search, "Search")
}
