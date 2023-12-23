package com.example.willog_unsplash.ui.events

import com.example.willog_unsplash.data.model.PhotoData

sealed class SearchEvent {
    object GetBookmarkedPhotoId : SearchEvent()
    object ClickBookMark : SearchEvent()
    data class GetSearchQuery(val query: String) : SearchEvent()
    data class ClickImage(val selectedImage: PhotoData) : SearchEvent()
}
