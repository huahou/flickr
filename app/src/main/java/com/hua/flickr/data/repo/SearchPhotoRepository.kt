package com.hua.flickr.data.repo

import com.hua.flickr.data.model.PhotoResponse
import com.hua.flickr.networking.FlickrApi
import retrofit2.Response
import javax.inject.Inject

class SearchPhotoRepository @Inject constructor(private val flickrApi: FlickrApi) {
    suspend fun searchPhotos(keyword: String): Response<PhotoResponse> {
        return flickrApi.searchPhotos(keyword)
    }
}