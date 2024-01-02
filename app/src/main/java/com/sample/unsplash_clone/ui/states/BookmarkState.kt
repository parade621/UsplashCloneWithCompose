package com.sample.unsplash_clone.ui.states

import com.sample.unsplash_clone.data.model.PhotoData

data class BookmarkState(
    val selectedImage: PhotoData? = null,
)