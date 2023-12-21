package com.example.willog_unsplash.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.willog_unsplash.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun navBack() {
        appNavigator.tryNavigateBack()
    }

}