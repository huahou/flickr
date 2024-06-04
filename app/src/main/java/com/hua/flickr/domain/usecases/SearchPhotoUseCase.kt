package com.hua.flickr.domain.usecases

import com.hua.flickr.data.repo.SearchPhotoRepository
import javax.inject.Inject

class SearchPhotoUseCase @Inject constructor(private val photosRepo: SearchPhotoRepository){
}