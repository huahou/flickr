package com.hua.flickr.dependencyinjection.app

import com.hua.flickr.dependencyinjection.activity.ActivityComponent
import com.hua.flickr.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(module: ActivityModule): ActivityComponent
}