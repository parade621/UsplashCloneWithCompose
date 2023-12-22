package com.example.willog_unsplash.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        CircularProgressIndicator(
            color = Color.Red,
            modifier = Modifier
                .align(Alignment.Center)
        )

    }
}

@Preview
@Composable
fun PreviewLoadingWheel() {
    LoadingWheel()
}