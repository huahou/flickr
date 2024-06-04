package com.hua.flickr.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface FlickrDestination {
    val icon: ImageVector
    val route: String
}

object Search : FlickrDestination {
    override val icon = Icons.Filled.Home
    override val route = "search"
}

object ImageDetail : FlickrDestination {
    override val icon = Icons.Filled.Details
    const val imageIndexArg = "image_index"
    override val route = "image_detail/{${imageIndexArg}}"
    const val baseRoute = "image_detail"
    val arguments = listOf(navArgument(imageIndexArg){ type = NavType.IntType})
}

val flickrScreens = listOf(Search, ImageDetail)