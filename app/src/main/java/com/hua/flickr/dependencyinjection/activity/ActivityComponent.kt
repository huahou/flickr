package com.hua.flickr.dependencyinjection.activity

import com.hua.flickr.ui.activities.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}