package com.example.willog_unsplash.ui.states

import com.example.willog_unsplash.data.model.PhotoData

data class DetailState(
    val init: Boolean = false,
    val photoInfo: PhotoData = PhotoData(),
    val showSnackbar: Boolean = false,
)
