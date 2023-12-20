package com.example.willog_unsplash.navigation

sealed class Screens(val route: String) {
    sealed class NoArgumentsScreen(route: String) : Screens(route) {
        operator fun invoke(): String = route
    }

    object SearchScreen : NoArgumentsScreen("search")

    object DetailScreen : NoArgumentsScreen("detail")

    object BookMarkScreen : NoArgumentsScreen("bookmarks")
}