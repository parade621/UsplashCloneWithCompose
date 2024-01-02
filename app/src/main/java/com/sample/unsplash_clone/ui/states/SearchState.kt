package com.sample.unsplash_clone.ui.states

import com.sample.unsplash_clone.data.model.PhotoData

data class SearchState(
    val isSearching: Boolean = false,
    val selectedImage: PhotoData? = null,
)
