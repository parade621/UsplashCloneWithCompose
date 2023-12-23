package com.example.willog_unsplash

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.navigation.NavigationIntent
import com.example.willog_unsplash.navigation.Screens
import com.example.willog_unsplash.ui.components.CustomTopAppBar
import com.example.willog_unsplash.ui.components.DetailInfo
import com.example.willog_unsplash.ui.components.ErrorScreen
import com.example.willog_unsplash.ui.components.ImageFrame
import com.example.willog_unsplash.ui.components.LoadingWheel
import com.example.willog_unsplash.ui.components.SearchBar
import com.example.willog_unsplash.ui.events.DetailEvent
import com.example.willog_unsplash.ui.events.SearchEvent
import com.example.willog_unsplash.ui.states.DetailState
import com.example.willog_unsplash.ui.states.SearchState
import com.example.willog_unsplash.ui.theme.Willog_UnsplashTheme
import com.example.willog_unsplash.ui.theme.backGround
import com.example.willog_unsplash.ui.viewmodel.DetailViewModel
import com.example.willog_unsplash.ui.viewmodel.RootViewModel
import com.example.willog_unsplash.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.URLEncoder

const val IntentValue = "IntentValue"

@AndroidEntryPoint
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
fun Unsplash(
    rootViewModel: RootViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = rootViewModel.navigationChannel,
        navHostController = navController
    )

    NavHost(navController = navController, startDestination = Screens.SearchScreen.route) {

        composable(
            route = Screens.SearchScreen.route
        ) {
            val viewModel: SearchViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val lazyPagingItems = viewModel.searchPagingResult.collectAsLazyPagingItems()

            SearchScreen(
                state = state,
                onEvent = viewModel::onEvent,
                lazyPagingItems = lazyPagingItems
            )
        }

        composable(
            route = Screens.DetailScreen.route.plus("/{$IntentValue}")
        ) { backStackEntry ->
            val viewModel: DetailViewModel = hiltViewModel()
            viewModel.onEvent(
                DetailEvent.FetchPhotoInfo(
                    backStackEntry.arguments?.getString(
                        IntentValue
                    ) ?: ""
                )
            )
            val state = viewModel.state.collectAsStateWithLifecycle().value
            DetailsScreen(state = state, onEvent = viewModel::onEvent)
        }

        composable(
            route = Screens.BookMarkScreen.route
        ) {
            BookmarksScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    hasBookMark: Boolean = false,
    title: String,
    onEvent: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = title,
                hasBookMark = hasBookMark,
                onBookMarkClick = onEvent
            )
        },
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
fun SearchScreen(
    state: SearchState = SearchState(),
    onEvent: (SearchEvent) -> Unit = { },
    lazyPagingItems: LazyPagingItems<PhotoData>
) {
    val query = rememberSaveable { mutableStateOf("") }

    BaseScreen(
        title = "Search",
        hasBookMark = true,
        onEvent = { onEvent(SearchEvent.ClickBookMark) }
    ) { _ ->
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                SearchBar(
                    hint = "Search",
                    text = query.value,
                    modifier = Modifier,
                    focusRequester = FocusRequester(),
                    visualTransformation = VisualTransformation.None,
                    getNewString = { newText ->
                        query.value = newText
                    }
                ) {
                    onEvent(SearchEvent.GetSearchQuery(query.value))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Component로 분리 예정
            if (state.isSearching) {
                // 지금 로딩하면 화면 깜빡거림 이거 수정 필요
                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                    items(lazyPagingItems.itemCount, key = { index ->
                        lazyPagingItems.peek(index)?.id ?: index
                    }) { index ->
                        val photoData = lazyPagingItems[index]
                        ImageFrame(
                            image = photoData?.urls?.small ?: "",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .border(0.5.dp, Color.White)
                        ) {
                            if (photoData != null)
                                onEvent(SearchEvent.ClickImage(photoData))
                        }
                    }
                }

                when {
                    lazyPagingItems.loadState.append is LoadState.Loading -> {
                        LoadingWheel()
                    }

                    lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                        LoadingWheel()
                    }

                    lazyPagingItems.loadState.refresh is LoadState.Error -> {
                        val e = lazyPagingItems.loadState.refresh as LoadState.Error
                        ErrorScreen(error = e.error)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    state: DetailState = DetailState(),
    onEvent: (DetailEvent) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Details",
                hasBookMark = false,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGround) // 기본 배경색 지정
                    .padding(paddingValues), // Scaffold로부터 제공받는 padding 적용
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        ImageFrame(/*image = image.url*/
                            image = state.photoInfo!!.urls.raw,
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp)),
                    ) {
                        DetailInfo(type = "ID", value = state.photoInfo!!.id)
                        Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
                        DetailInfo(type = "Author", value = state.photoInfo.user.username)
                        Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
                        DetailInfo(
                            type = "Size",
                            value = "${state.photoInfo.width} x ${state.photoInfo.height}"
                        )
                        Divider(color = Color(0xFFE9E9E9), thickness = 1.dp)
                        DetailInfo(type = "Created At", value = state.photoInfo.created_at)
                    }
                }
            }
        }
    )
}


@Composable
fun BookmarksScreen() {
    BaseScreen(
        title = "Bookmark",
        hasBookMark = false
    ) { _ ->

        // TODO: 북마크된 이미지 목록 표시
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
//            items(images.size) { index ->
//                ImageFrame(/*image = image.url*/) {
//                    //navController.navigate("details/${image.id}")
//                }
//            }
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(activity, navHostController, navigationChannel) {

        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {

                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    val route = if (intent.extra.isNotEmpty()) {
                        val extra = URLEncoder.encode(intent.extra, "UTF-8")
                        intent.route + "/" + extra
                    } else {
                        intent.route
                    }

                    navHostController.navigate(route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }

                }
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