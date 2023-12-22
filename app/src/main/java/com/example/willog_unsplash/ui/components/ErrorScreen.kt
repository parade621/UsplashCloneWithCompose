package com.example.willog_unsplash.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(error: Throwable) {
    // UI detail 수정 시, 수정 예정
    Text(text = "Error: ${error.localizedMessage}")
}

@Preview
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(error = Throwable("Error Message"))
}