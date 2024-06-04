package com.hua.flickr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hua.flickr.R
import com.hua.flickr.data.model.Photo
import com.hua.flickr.domain.usecases.SearchPhotoUseCase

@Composable
fun SearchScreen(
    photosState: SearchPhotoUseCase.PhotosUiState,
    onSearch: (String) -> Unit,
    onImageClick: (Int) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(onSearch)

        when (photosState) {
            is SearchPhotoUseCase.PhotosUiState.Initial -> {
                InitialUI()
            }

            is SearchPhotoUseCase.PhotosUiState.Loading -> {
                LoadingUI()
            }

            is SearchPhotoUseCase.PhotosUiState.Success -> {
                PhotosUI(photosState.photos, onImageClick)
            }

            is SearchPhotoUseCase.PhotosUiState.Failure -> {
                ErrorUI()
            }
        }
    }
}

@Composable
private fun InitialUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.flickr),
            contentDescription = "Flickr logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
private fun LoadingUI() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun PhotosUI(
    photos: List<Photo>,
    onImageClick: (Int) -> Unit
) {
    if (photos.isEmpty()) {
        EmptyResultUI()
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 2.dp,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            itemsIndexed(photos) { index, photo ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onImageClick.invoke(index)
                        }
                )
            }
        }
    }
}

@Composable
private fun EmptyResultUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.no_result_found),
            style = typography.bodyMedium
        )
    }
}

@Composable
private fun SearchBar(onSearch: (String) -> Unit) {
    val searchText = remember { mutableStateOf("") }
    TextField(
        value = searchText.value,
        onValueChange = { newText ->
            searchText.value = newText
            onSearch(newText)
        },
        textStyle = typography.bodyMedium,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(id = R.string.search_placeholder_text))
        },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
    )
}

@Composable
private fun ErrorUI() {

}