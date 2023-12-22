package com.example.willog_unsplash.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.willog_unsplash.R
import com.example.willog_unsplash.ui.theme.lightGray
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageFrame(
    image: String = "",
    modifier: Modifier = Modifier,
    onEvent: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(lightGray)
            .clickable(
                onClick = { /*TODO*/ }
            )
    ) {
        GlideImage(
            imageModel = { image },
            modifier = Modifier
                .fillMaxSize(),
            previewPlaceholder = /*이미지가 없을 때*/ R.drawable.ic_launcher_foreground,
        )
    }
}

@Preview
@Composable
fun PreviewImageFrame() {
    ImageFrame()
}