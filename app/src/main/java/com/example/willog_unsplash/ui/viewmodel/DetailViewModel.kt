package com.example.willog_unsplash.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.repository.PhotoSearchRepo
import com.example.willog_unsplash.navigation.AppNavigator
import com.example.willog_unsplash.ui.events.DetailEvent
import com.example.willog_unsplash.ui.states.DetailState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val moshi: Moshi,
    private val photoSearchRepo: PhotoSearchRepo
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.FetchPhotoInfo -> fetchPhotoInfo(event.photoData)
            is DetailEvent.InsertBookMark -> insertBookMark()
            is DetailEvent.DeleteBookMark -> deleteBookMark()
            is DetailEvent.NavBack -> navBack()
            else -> {} //?
        }
    }

    private fun fetchPhotoInfo(photoData: String) {

        if (photoData.isEmpty()) {
            _state.update {
                it.copy(photoInfo = PhotoData())
            }
            return
        }

        val adapter = moshi.adapter(PhotoData::class.java)
        val photoDataJson: PhotoData = adapter.fromJson(photoData)!!
        photoDataJson.let { newData ->
            //_state.value = _state.value.copy(photoInfo = newData)
            _state.update {
                it.copy(photoInfo = newData)
            }
        }
    }

    private fun insertBookMark() {
        Timber.e("${state.value.photoInfo.isBookmarked} 이걸 바꿀거야")
        _state.update {
            it.copy(
                photoInfo =
                _state.value.photoInfo.copy(isBookmarked = true)
            )
        }
        Timber.e("값 바뀜? ${state.value.photoInfo.isBookmarked}")
        viewModelScope.launch(Dispatchers.IO) {
            photoSearchRepo.insertBookMark(state.value.photoInfo)
        }
    }

    private fun deleteBookMark() {
        _state.update {
            it.copy(
                photoInfo =
                _state.value.photoInfo.copy(isBookmarked = false)
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            photoSearchRepo.deleteBookMark(state.value.photoInfo)
        }
    }

    private fun navBack() {
        appNavigator.tryNavigateBack()
    }
}