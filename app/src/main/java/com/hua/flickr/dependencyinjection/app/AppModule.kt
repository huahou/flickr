package com.hua.flickr.dependencyinjection.app

import android.app.Application
import com.hua.flickr.common.Constants
import com.hua.flickr.networking.FlickrApi
import com.hua.flickr.networking.SSLPinning
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(val app: Application) {

    @Provides
    fun okHttpClient(): OkHttpClient = SSLPinning.getPinnedClient(app)
        .newBuilder()
        .build()

    @Provides
    @AppScope
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.FLICKR_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun application() = app

    @Provides
    @AppScope
    fun weatherApi(retrofit: Retrofit): FlickrApi = retrofit.create(FlickrApi::class.java)
}