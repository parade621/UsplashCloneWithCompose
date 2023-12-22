package com.example.willog_unsplash.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willog_unsplash.ui.theme.ligthHeavyGray
import com.example.willog_unsplash.ui.theme.normalGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String, hasBookMark: Boolean = true) {
    Column {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ligthHeavyGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    BookMark(
                        modifier = Modifier
                            .size(30.dp)
                            .alpha(if (hasBookMark) 1f else 0f)
                    )
                }
            }
        )
        Divider(color = normalGray, thickness = 1.dp)
    }
}

@Preview
@Composable
fun PreviewTopAppBar() {
    CustomTopAppBar(title = "Title")
}