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
import com.sample.unsplash_clone.ui.events.SearchEvent
import com.sample.unsplash_clone.ui.states.SearchState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val photoSearchRepo: PhotoSearchRepo,
    private val moshi: Moshi
) : ViewModel() {

    private var dbData = setOf<String>()

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    // Paging
    private val _searchPagingResult: MutableStateFlow<PagingData<PhotoData>> =
        MutableStateFlow<PagingData<PhotoData>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<PhotoData>> = _searchPagingResult.asStateFlow()


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.GetBookmarkedPhotoId -> getBookmarkedPhotoId()
            is SearchEvent.ClickBookMark -> clickBookMark()
            is SearchEvent.GetSearchQuery -> searchImage(event.query)
            is SearchEvent.ClickImage -> clickImage(event.selectedImage)
        }
    }

    private fun getBookmarkedPhotoId() {
        viewModelScope.launch(Dispatchers.IO) {
            val tmpData = photoSearchRepo.getAllBookmarkId()
            dbData = tmpData.toSet()

        }
    }

    private fun searchImage(query: String) {

        _state.update { it.copy(isSearching = true) }

        viewModelScope.launch(Dispatchers.IO) {
            photoSearchRepo.searchPhotosPaging(query)
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.map { photo ->
                        photo.copy(isBookmarked = dbData.contains(photo.id))
                    }
                }
                .collect { pagingData ->
                    _searchPagingResult.value = pagingData
                }
        }
    }

    private fun clickImage(selectedImage: PhotoData) {

        val adapter = moshi.adapter(PhotoData::class.java)
        val photoData = adapter.toJson(selectedImage)

        appNavigator.tryNavigateTo(
            Screens.DetailScreen(),
            popUpToRoute = Screens.SearchScreen(),
            extra = photoData
        )
    }

    private fun clickBookMark() {
        appNavigator.tryNavigateTo(
            Screens.BookMarkScreen(),
            popUpToRoute = Screens.SearchScreen(),
        )
    }
}