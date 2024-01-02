package com.sample.unsplash_clone.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sample.unsplash_clone.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RootViewModel @Inject constructor(
    appNavigator: AppNavigator
) : ViewModel() {

    val navigationChannel = appNavigator.navigationChannel
}
