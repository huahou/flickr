package com.hua.flickr.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hua.flickr.data.model.Photo
import com.hua.flickr.domain.usecases.SearchPhotoUseCase
import com.hua.flickr.ui.common.activities.BaseActivity
import com.hua.flickr.ui.common.viewmodels.ViewModelFactory
import com.hua.flickr.ui.components.FlickrAppBar
import com.hua.flickr.ui.navigation.ImageDetail
import com.hua.flickr.ui.navigation.Search
import com.hua.flickr.ui.navigation.flickrScreens
import com.hua.flickr.ui.screens.PhotoDetailScreen
import com.hua.flickr.ui.screens.SearchScreen
import com.hua.flickr.ui.theme.FlickrTheme
import com.hua.flickr.ui.viewmodels.FlickrViewModel
import com.hua.flickr.utils.ext.navigateSingleTopTo
import com.hua.flickr.utils.ext.share
import javax.inject.Inject


class MainActivity : BaseActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory<FlickrViewModel>
    lateinit var viewModel: FlickrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FlickrViewModel::class.java)

        enableEdgeToEdge()
        viewModel.photoState.observe(this) { photosState ->
            setContent {
                FlickrApp(photosState = photosState) { keyword ->
                    viewModel.searchPhotos(keyword)
                }
            }
        }
        if (savedInstanceState == null)
            viewModel.searchPhotos("")
    }

    @Composable
    private fun FlickrApp(photosState: SearchPhotoUseCase.PhotosUiState, onSearch: (String) -> Unit ) {
        FlickrTheme {
            val navController: NavHostController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()

            Scaffold(
                topBar = {
                    FlickrAppBar(
                        currentScreen = flickrScreens.find {
                            it.route == currentBackStack?.destination?.route
                        } ?: Search
                    ){
                        navController.navigateUp()
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Search.route,
                    modifier = Modifier.padding(innerPadding)
                ){
                    composable(route = Search.route) {
                        SearchScreen(
                            photosState = photosState,
                            onSearch = onSearch,
                            onImageClick = { index ->
                                navController.navigateSingleTopTo("${ImageDetail.baseRoute}/${index}")
                            }
                        )
                    }

                    composable(
                        route = ImageDetail.route,
                        arguments = ImageDetail.arguments
                    ) { navBackStackEntry ->
                        val photoIndex = navBackStackEntry.arguments?.getInt(ImageDetail.imageIndexArg)
                        photoIndex?.let{
                            val image = (photosState as SearchPhotoUseCase.PhotosUiState.Success).photos[photoIndex]
                            PhotoDetailScreen(image) {
                                sharePhoto(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sharePhoto(photo: Photo) {
        val sharedText = "${photo.title} ${photo.thumbnailUrl}"
        share(sharedText)
    }
}
