package com.hua.flickr.domain.usecases

import com.hua.flickr.data.model.Photo
import com.hua.flickr.data.repo.SearchPhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SearchPhotoUseCase @Inject constructor(private val photosRepo: SearchPhotoRepository){
    sealed class PhotosUiState {
        object Initial: PhotosUiState()
        object Loading: PhotosUiState()
        class Success(val photos: List<Photo>) : PhotosUiState()
        object Failure: PhotosUiState()
    }


    suspend fun searchPhotos(keyword: String): PhotosUiState {
        if (keyword.isEmpty()) return PhotosUiState.Initial

        return withContext(Dispatchers.IO) {
            try {
                val response = photosRepo.searchPhotos(keyword)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext PhotosUiState.Success(response.body()!!.photos)
                } else {
                    return@withContext PhotosUiState.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext PhotosUiState.Failure
                } else {
                    throw t
                }
            }
        }
    }
}