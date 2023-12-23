package com.example.willog_unsplash.ui.states

import com.example.willog_unsplash.data.model.PhotoData

data class SearchState(
    val isSearching: Boolean = false,
    val selectedImage: PhotoData? = null,
)
