package com.example.willog_unsplash.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewLoginTextField() {
    SearchBar(
        hint = "Search",
        modifier = Modifier,
        focusRequester = FocusRequester(),
        getNewString = { },
        visualTransformation = VisualTransformation.None
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    hint: String,
    text: String = "",
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    getNewString: (String) -> Unit,
    visualTransformation: VisualTransformation
) {
    OutlinedTextField(
        value = text,
        onValueChange = { newValue ->
            getNewString(newValue)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = normalGray,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(20.dp), true)
            .background(
                color = lightGray,
                shape = RoundedCornerShape(20.dp),
            )
            .focusRequester(focusRequester = focusRequester),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = normalGray
        ),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        },
        placeholder = {
            Text(
                text = hint,
                color = normalGray,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        visualTransformation = visualTransformation
    )
}