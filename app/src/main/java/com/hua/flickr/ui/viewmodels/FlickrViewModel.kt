package com.hua.flickr.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hua.flickr.domain.usecases.SearchPhotoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlickrViewModel @Inject constructor(private val searchPhotoUseCase: SearchPhotoUseCase): ViewModel() {
    private val _photoState: MutableLiveData<SearchPhotoUseCase.PhotosUiState> = MutableLiveData()
    val photoState: LiveData<SearchPhotoUseCase.PhotosUiState> = _photoState

    fun searchPhotos(keyword: String) {
        _photoState.value = SearchPhotoUseCase.PhotosUiState.Loading
        viewModelScope.launch {
            try {
                _photoState.value = searchPhotoUseCase.searchPhotos(keyword)
            } catch (t: Throwable) {
                _photoState.value = SearchPhotoUseCase.PhotosUiState.Failure
            }
        }

    }
}