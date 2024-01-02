package com.sample.unsplash_clone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sample.unsplash_clone.data.model.PhotoData
import com.sample.unsplash_clone.data.repository.PhotoSearchRepo
import com.sample.unsplash_clone.navigation.AppNavigator
import com.sample.unsplash_clone.navigation.Screens
import com.sample.unsplash_clone.ui.events.BookmarkEvent
import com.sample.unsplash_clone.ui.states.BookmarkState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val photoSearchRepo: PhotoSearchRepo,
    private val moshi: Moshi
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()

    private val _bookmarkPagingResult: MutableStateFlow<PagingData<PhotoData>> =
        MutableStateFlow<PagingData<PhotoData>>(PagingData.empty())
    val bookmarkPagingResult: StateFlow<PagingData<PhotoData>> = _bookmarkPagingResult.asStateFlow()


    fun onEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.LoadBookmark -> getBookmarkList()
            is BookmarkEvent.ClickImage -> clickImage(event.selectedImage)
        }
    }

    private fun getBookmarkList() {
        viewModelScope.launch(Dispatchers.IO) {
            photoSearchRepo.getBookmarkedPhotos()
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.map { photo ->
                        photo.copy(isBookmarked = true)
                    }
                }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
                .collect { photoDatas ->
                    _bookmarkPagingResult.value = photoDatas
                }
        }
    }

    private fun clickImage(selectedImage: PhotoData) {

        val adapter = moshi.adapter(PhotoData::class.java)
        val photoData = adapter.toJson(selectedImage)

        appNavigator.tryNavigateTo(
            Screens.DetailScreen(),
            popUpToRoute = Screens.BookMarkScreen(),
            extra = photoData
        )
    }
}