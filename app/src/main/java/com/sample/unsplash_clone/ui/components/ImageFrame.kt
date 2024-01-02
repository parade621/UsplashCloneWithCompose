package com.sample.unsplash_clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.unsplash_clone.R
import com.sample.unsplash_clone.ui.theme.lightGray
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageFrame(
    image: String = "",
    modifier: Modifier = Modifier,
    isBookMarked: Boolean = false,
    onEvent: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(lightGray)
            .clickable(
                onClick = onEvent
            )
    ) {
        GlideImage(
            imageModel = { image },
            modifier = Modifier
                .fillMaxSize(),
            previewPlaceholder = /*이미지가 없을 때*/ R.drawable.ic_launcher_foreground,
        )

        if (isBookMarked) {
            BookMark(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(3.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageFrame() {
    ImageFrame()
}