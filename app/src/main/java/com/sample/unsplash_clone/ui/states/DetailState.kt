package com.sample.unsplash_clone.ui.states

import com.sample.unsplash_clone.data.model.PhotoData

data class DetailState(
    val init: Boolean = false,
    val photoInfo: PhotoData = PhotoData(),
)
