package com.example.willog_unsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.willog_unsplash.navigation.Screens
import com.example.willog_unsplash.ui.theme.CustomTopAppBar
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

    BaseScreen(snackbarHostState = snackbarHostState, title = "App Title") { paddingValues ->

        NavHost(navController = navController, startDestination = "search") {

            composable(
                route = Screens.SearchScreen.route
            ) { SearchScreen(navController) }

            composable("details") { backStackEntry ->
                DetailsScreen(navController, backStackEntry.arguments?.getString("imageId"))
            }

            composable("bookmarks") { BookmarksScreen(navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    snackbarHostState: SnackbarHostState,
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { CustomTopAppBar(title = title) },
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
        }
    )
}

@Composable
fun SearchScreen(navController: NavController) {
    var query by rememberSaveable { mutableStateOf("") }
//    val images = remember { mutableStateListOf<ImageModel>() } // ImageModel은 이미지 데이터를 나타내는 데이터 클래스

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            SearchBar(
                hint = "Search",
                modifier = Modifier,
                focusRequester = FocusRequester(),
                getNewString = { },
                visualTransformation = VisualTransformation.None
            )

            /* Glid LazyColumn in here */
        }
//        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
//            items(images) { image ->
//                ImageItem(image = image) {
//                    navController.navigate("details/${image.id}")
//                }
//            }
//        }
    }
}

@Composable
fun DetailsScreen(navController: NavController, imageId: String?) {
    // TODO: API를 통해 상세 이미지 정보를 가져오기
    // imageId를 사용하여 특정 이미지의 상세 정보를 표시
}

@Composable
fun BookmarksScreen(navController: NavController) {
    // TODO: 북마크된 이미지 목록 표시
}


@Preview
@Composable
fun DefaultPreview() {
    Willog_UnsplashTheme {
        Unsplash()
    }
}