package com.example.willog_unsplash.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookMark(
    modifier: Modifier = Modifier,
    isBookMark: Boolean = false
) {
    val isBookMark = remember { MutableInteractionSource() } // 클릭 피드백 없애기

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                onClick = { isBookMark= !isBookMark }
            )
    ) {
        Image(
            imageVector = if()Icons.Default.FavoriteBorder,
            contentDescription = "BookMark"
        )
    }
}

@Preview
@Composable
fun PreviewBookMark() {
    BookMark()
}