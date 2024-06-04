package com.hua.flickr.ui.screens

import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.hua.flickr.R
import com.hua.flickr.data.model.Photo
import com.hua.flickr.ui.components.HtmlText
import kotlinx.coroutines.Dispatchers

@Composable
fun PhotoDetailScreen(photo: Photo) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    val context = LocalContext.current
    val placeholder = R.drawable.placeholder
    val imageUrl = photo.thumbnailUrl

    var imageWidth by remember { mutableStateOf(0) }
    var imageHeight by remember { mutableStateOf(0) }

    // Build an ImageRequest with Coil
    val listener = object : ImageRequest.Listener {
        override fun onSuccess(request: ImageRequest, result: SuccessResult) {
            val drawable = result.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                imageWidth = bitmap.width
                imageHeight = bitmap.height
            }
            super.onSuccess(request, result)
        }
    }
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .listener(listener)
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    if (isPortrait) {
        ImageDetailPortraitMode(photo = photo, imageRequest = imageRequest, width = imageWidth, height = imageHeight)
    } else {
        ImageDetailLandscapeMode(photo = photo, imageRequest = imageRequest, width = imageWidth, height = imageHeight)
    }
}

@Composable
private fun ImageDetailPortraitMode(photo: Photo, imageRequest: ImageRequest, width: Int, height: Int){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = "Image Description",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        DetailsSection(photo = photo, width = width, height = height)
    }
}

@Composable
private fun ImageDetailLandscapeMode(photo: Photo, imageRequest: ImageRequest, width: Int, height: Int){
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = "Image Description",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )
        Spacer(modifier = Modifier.height(8.dp))

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            DetailsSection(photo = photo, width = width, height = height)
        }
    }
}

@Composable
private fun DetailsSection(photo: Photo, width: Int, height: Int) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = photo.title,
            style = typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = stringResource(id = R.string.credit),
                style = typography.titleSmall
            )
            Text(
                text = photo.author,
                style = typography.bodyMedium
            )
        }

        Row {
            Text(
                text = stringResource(id = R.string.size),
                style = typography.titleSmall
            )
            Text(text = "$width x $height")
        }

        Text(
            text = stringResource(id = R.string.description),
            style = typography.titleSmall
        )

        HtmlText(html = photo.description)

        Button(
            onClick = { /* Do something */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_500)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Share",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}