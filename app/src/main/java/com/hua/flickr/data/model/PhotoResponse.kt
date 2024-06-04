package com.hua.flickr.data.model

import com.google.gson.annotations.SerializedName


data class PhotoResponse(
    @SerializedName("items") val photos: List<Photo>
)