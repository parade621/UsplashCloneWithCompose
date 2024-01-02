package com.sample.unsplash_clone.ui.events

import com.sample.unsplash_clone.data.model.PhotoData

sealed class BookmarkEvent {
    object LoadBookmark : BookmarkEvent()
    data class ClickImage(val selectedImage: PhotoData) : BookmarkEvent()
}
