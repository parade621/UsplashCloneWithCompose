package com.example.willog_unsplash.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.navigation.AppNavigator
import com.example.willog_unsplash.ui.events.DetailEvent
import com.example.willog_unsplash.ui.states.DetailState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.stream.Collectors.toList
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val moshi: Moshi
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.FetchPhotoInfo -> fetchPhotoInfo(event.photoData)
            is DetailEvent.ClickBookMark -> {/*TODO*/
            }

            is DetailEvent.NavBack -> navBack()
            else -> {}
        }
    }

    private fun fetchPhotoInfo(photoData: String) {
        if (photoData.isEmpty()) return
        val adapter = moshi.adapter(PhotoData::class.java)
        val photoData = adapter.fromJson(photoData)
        photoData.let { newData ->
            _state.value = _state.value.copy(photoInfo = newData)
        }
    }

    private fun navBack() {
        appNavigator.tryNavigateBack()
    }
}