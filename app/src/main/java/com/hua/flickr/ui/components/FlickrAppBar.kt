package com.hua.flickr.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hua.flickr.R
import com.hua.flickr.ui.navigation.FlickrDestination
import com.hua.flickr.ui.navigation.ImageDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickrAppBar(currentScreen: FlickrDestination, onBack: () -> Unit) {
    val titleText = when (currentScreen) {
        ImageDetail -> {
            stringResource(id = R.string.photo_detail)
        }
        else -> {
            // Home screen
            stringResource(id = R.string.flickr)
        }
    }
    TopAppBar(
        title = {
            Text(
                text = titleText,
                style = typography.titleMedium
            )
        },
        navigationIcon = {
            if (currentScreen.route == ImageDetail.route) {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            } else {
                IconButton(onClick = { /* Handle home icon click */ }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
            }
        }
    )
}