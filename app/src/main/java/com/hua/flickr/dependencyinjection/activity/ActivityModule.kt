package com.hua.flickr.dependencyinjection.activity

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun activity(): Activity = activity

}