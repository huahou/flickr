package com.hua.flickr.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.hua.flickr.domain.usecases.SearchPhotoUseCase
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val searchPhotoUseCase: SearchPhotoUseCase): ViewModel() {
}