package com.hua.flickr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hua.flickr.data.model.Media
import com.hua.flickr.data.model.Photo
import com.hua.flickr.domain.usecases.SearchPhotoUseCase
import com.hua.flickr.ui.viewmodels.FlickrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FlickrViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockSearchPhotoUseCase: SearchPhotoUseCase

    @Mock
    private lateinit var mockObserver: Observer<SearchPhotoUseCase.PhotosUiState>

    private lateinit var viewModel: FlickrViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockSearchPhotoUseCase = mock(SearchPhotoUseCase::class.java)
        viewModel = FlickrViewModel(mockSearchPhotoUseCase)
        viewModel.photoState.observeForever(mockObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun flickrViewModel_photoSuccessfullyLoaded_photosUpdated() = runTest {
        val photos = listOf(
            Photo(
                title = "Rocky Mountain",
                description = "This is a beautiful place",
                author = "David Reed",
                media = Media("https://live.staticflickr.com/65535/53769361124_4f4e902d92_m.jpg")
            ),
            Photo(
                title = "Porcupine",
                description = "this spring, my wife and I have seen an abundance of porcupines",
                author = "obody@flickr.com",
                media = Media("https://live.staticflickr.com/65535/53758695904_4954b2d59f_m.jpg")
            )
        )
        val photosState = SearchPhotoUseCase.PhotosUiState.Success(photos)

        `when`(mockSearchPhotoUseCase.searchPhotos("Porcupine")).thenReturn(photosState)

        viewModel.searchPhotos("Porcupine")

        advanceUntilIdle()

        val photoUiState = viewModel.photoState.value
        assertNotNull(photoUiState)
        assertTrue(photoUiState is SearchPhotoUseCase.PhotosUiState.Success)
        assertEquals(photos.size, (photoUiState as SearchPhotoUseCase.PhotosUiState.Success).photos.size)
    }
}