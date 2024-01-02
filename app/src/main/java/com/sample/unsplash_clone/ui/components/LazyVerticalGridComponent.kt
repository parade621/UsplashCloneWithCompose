package com.sample.unsplash_clone.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.sample.unsplash_clone.data.model.PhotoData

@Composable
fun LazyVerticalGridComponent(
    lazyPagingItems: LazyPagingItems<PhotoData>,
    isBookmarked: Boolean = false,
    onEvent: (PhotoData) -> Unit
) {
    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(lazyPagingItems.itemCount, key = { index ->
            lazyPagingItems.peek(index)?.id ?: index
        }) { index ->
            val photoData = lazyPagingItems[index]
            ImageFrame(
                image = photoData?.urls?.small ?: "",
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(0.5.dp, Color.White),
                isBookMarked = if (!isBookmarked) photoData?.isBookmarked ?: false else true
            ) {
                if (photoData != null)
                    onEvent(photoData)
            }
        }
    }
}