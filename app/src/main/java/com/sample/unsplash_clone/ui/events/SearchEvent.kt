package com.sample.unsplash_clone.ui.events

import com.sample.unsplash_clone.data.model.PhotoData

sealed class SearchEvent {
    object GetBookmarkedPhotoId : SearchEvent()
    object ClickBookMark : SearchEvent()
    data class GetSearchQuery(val query: String) : SearchEvent()
    data class ClickImage(val selectedImage: PhotoData) : SearchEvent()
}
