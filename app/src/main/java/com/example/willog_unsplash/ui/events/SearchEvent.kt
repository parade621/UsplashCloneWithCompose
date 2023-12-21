package com.example.willog_unsplash.ui.events

sealed class SearchEvent {
    data class GetSearchQuery(val query: String) : SearchEvent()
    object ClickImage : SearchEvent()
    object ClickBookMark : SearchEvent()
}
