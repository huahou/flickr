package com.hua.flickr.ui.common.activities

import androidx.activity.ComponentActivity
import com.hua.flickr.dependencyinjection.activity.ActivityComponent
import com.hua.flickr.dependencyinjection.activity.ActivityModule

open class BaseActivity: ComponentActivity() {
    private val activityComponent: ActivityComponent by lazy {
        (application as com.hua.flickr.app.FlickrApp).appComponent.newActivityComponent(
            ActivityModule(this)
        )
    }

    protected val injector by lazy { activityComponent }
}