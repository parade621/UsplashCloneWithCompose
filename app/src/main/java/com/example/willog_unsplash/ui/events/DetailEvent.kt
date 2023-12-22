package com.example.willog_unsplash.ui.events

sealed class DetailEvent {
    data class FetchPhotoInfo(val photoData: String) : DetailEvent()
    object ClickBookMark : DetailEvent()
    object NavBack : DetailEvent()
}
