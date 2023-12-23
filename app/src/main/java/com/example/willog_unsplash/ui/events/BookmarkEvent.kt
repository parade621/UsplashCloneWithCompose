package com.example.willog_unsplash.ui.events

import com.example.willog_unsplash.data.model.PhotoData

sealed class BookmarkEvent {
    object LoadBookmark : BookmarkEvent()
    data class ClickImage(val selectedImage: PhotoData) : BookmarkEvent()
}
