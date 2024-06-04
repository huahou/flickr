package com.hua.flickr.networking

import com.hua.flickr.data.model.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("/photos_public.gne?format=json&nojsoncallback=1&tags={tags}")
    suspend fun searchPhotos(@Query("tags") keyword: String): Response<PhotoResponse>
}