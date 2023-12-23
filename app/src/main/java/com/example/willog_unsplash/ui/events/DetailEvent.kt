package com.example.willog_unsplash.ui.events

sealed class DetailEvent {
    data class FetchPhotoInfo(val photoData: String) : DetailEvent()
    object InsertBookMark : DetailEvent()
    object DeleteBookMark : DetailEvent()
    object NavBack : DetailEvent()
}
