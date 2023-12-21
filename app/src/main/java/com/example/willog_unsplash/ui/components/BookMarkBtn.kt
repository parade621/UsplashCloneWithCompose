package com.example.willog_unsplash.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BookMarkBtn() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(2.dp, CircleShape)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .clickable(
                onClick = { /* TODO */ },
            )
            .padding(8.dp)
    ) {
        Image(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "BookMark"
        )
    }
}

@Preview
@Composable
fun PreviewBookMarkBtn() {
    BookMarkBtn()
}