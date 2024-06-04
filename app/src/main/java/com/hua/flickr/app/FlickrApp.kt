package com.hua.flickr.app

import android.app.Application
import com.hua.flickr.dependencyinjection.app.AppComponent
import com.hua.flickr.dependencyinjection.app.AppModule
import com.hua.flickr.dependencyinjection.app.DaggerAppComponent

class FlickrApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}