package com.example.willog_unsplash.ui.states

import com.example.willog_unsplash.data.model.PhotoData

data class DetailState(
    val photoInfo: PhotoData = PhotoData(),
    // delete시 Snackbar 보여주기 위함(기능 추가 고려 중 - 시간 남으면 ㄱ)
    val showSnackbar: Boolean = false,
)
