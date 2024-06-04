package com.hua.flickr.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("title") val title: String,
    @SerializedName("link") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("author") val author: String,
    @SerializedName("media") val media: Media
) {
    val thumbnailUrl: String
        get() = media.thumbnailUrl
}

data class Media(
    @SerializedName("m") val thumbnailUrl: String
)