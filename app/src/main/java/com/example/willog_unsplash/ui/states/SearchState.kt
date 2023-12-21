package com.example.willog_unsplash.ui.states

import com.example.willog_unsplash.data.model.PhotoData

data class SearchState(
    val query: String = "",
    val photos: List<PhotoData> = listOf(),
    val isLoading: Boolean = false,
    val error: String = ""
)
