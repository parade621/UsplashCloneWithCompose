package com.sample.unsplash_clone.ui.events

sealed class DetailEvent {
    data class FetchPhotoInfo(val photoData: String) : DetailEvent()
    object InsertBookMark : DetailEvent()
    object DeleteBookMark : DetailEvent()
    object NavBack : DetailEvent()
}
