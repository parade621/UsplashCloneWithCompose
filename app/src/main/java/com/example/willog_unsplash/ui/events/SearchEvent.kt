package com.example.willog_unsplash.ui.events

import com.example.willog_unsplash.data.model.PhotoData

sealed class SearchEvent {
    data class GetSearchQuery(val query: String) : SearchEvent()
    data class ClickImage(val selectedImage: PhotoData) : SearchEvent()
    object ClickBookMark : SearchEvent()
}
