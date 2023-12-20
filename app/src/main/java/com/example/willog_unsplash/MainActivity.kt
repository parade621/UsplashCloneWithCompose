package com.example.willog_unsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.willog_unsplash.data.PhotoData
import com.example.willog_unsplash.navigation.Screens
import com.example.willog_unsplash.ui.theme.CustomTopAppBar
import com.example.willog_unsplash.ui.theme.DetailInfo
import com.example.willog_unsplash.ui.theme.ImageFrame
import com.example.willog_unsplash.ui.theme.SearchBar
import com.example.willog_unsplash.ui.theme.Willog_UnsplashTheme
import com.example.willog_unsplash.ui.theme.backGround

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Willog_UnsplashTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Unsplash()
                }
            }
        }
    }
}

@Composable
fun Unsplash() {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }

    val title = remember { mutableStateOf("App Title") }
    val hasBookMark = remember { mutableStateOf(false) }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        title = title.value,
        hasBookMark = hasBookMark.value
    ) { paddingValues ->

        NavHost(navController = navController, startDestination = Screens.DetailScreen.route) {

            composable(
                route = Screens.SearchScreen.route
            ) {
                title.value = "Search"
                hasBookMark.value = true
                SearchScreen(navController)
            }

            composable(
                route = Screens.DetailScreen.route
            ) { backStackEntry ->
                title.value = "Details"
                hasBookMark.value = false
                DetailsScreen(navController, backStackEntry.arguments?.getString("imageId"))
            }

            composable(
                route = Screens.BookMarkScreen.route
            ) {
                title.value = "Bookmarks"
                hasBookMark.value = false
                BookmarksScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    hasBookMark: Boolean = false,
    snackbarHostState: SnackbarHostState,
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { CustomTopAppBar(title = title, hasBookMark = hasBookMark) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGround) // 기본 배경색 지정
                    .padding(paddingValues), // Scaffold로부터 제공받는 padding 적용
                contentAlignment = Alignment.TopStart
            ) {
                content(paddingValues)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* 클릭 시 수행할 액션 */ },
                shape = CircleShape,
                containerColor = Color.White,
            ) {
                Icon(
                    Icons.Filled.FavoriteBorder,
                    contentDescription = "BookMark",
                    tint = Color.Red
                )
            }
        }
    )
}

@Composable
fun SearchScreen(navController: NavController) {
    var query by rememberSaveable { mutableStateOf("") }
    val images =
        remember { mutableStateListOf<PhotoData>() } // Assuming PhotoData has a 'url' and 'id' property

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            SearchBar(
                hint = "Search",
                modifier = Modifier,
                focusRequester = FocusRequester(),
                getNewString = { /* handle new string */ },
                visualTransformation = VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                items(images.size) { index ->
                    ImageFrame(/*image = image.url*/) {
                        //navController.navigate("details/${image.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(navController: NavController, imageId: String?) {
    // TODO: API를 통해 상세 이미지 정보를 가져오기
    // imageId를 사용하여 특정 이미지의 상세 정보를 표시
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            ImageFrame(/*image = image.url*/
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                //navController.navigate("details/${image.id}")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
        ) {
            DetailInfo(type = "ID", value = "value")
            Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
            DetailInfo(type = "Author", value = "value")
            Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
            DetailInfo(type = "Size", value = "value")
            Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
            DetailInfo(type = "Created At", value = "value")
        }
    }
}

@Composable
fun BookmarksScreen(navController: NavController) {

    val images =
        remember { mutableStateListOf<PhotoData>() } // Assuming PhotoData has a 'url' and 'id' property

    // TODO: 북마크된 이미지 목록 표시
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(images.size) { index ->
            ImageFrame(/*image = image.url*/) {
                //navController.navigate("details/${image.id}")
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    Willog_UnsplashTheme {
        Unsplash()
    }
}