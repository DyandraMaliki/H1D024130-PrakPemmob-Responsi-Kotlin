package com.pemmob.responsisatu.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.pemmob.responsisatu.data.model.Book
import com.pemmob.responsisatu.ui.screen.DetailScreen
import com.pemmob.responsisatu.ui.screen.HomeScreen
import com.pemmob.responsisatu.ui.viewmodel.BookViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{bookJson}") {
        fun createRoute(book: Book): String {
            val json = Gson().toJson(book)
            return "detail/${Uri.encode(json)}"
        }
    }
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: BookViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onBookClick = { book ->
                    navController.navigate(Screen.Detail.createRoute(book))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("bookJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val bookJson = backStackEntry.arguments?.getString("bookJson")
            val book = bookJson?.let {
                try {
                    Gson().fromJson(it, Book::class.java)
                } catch (e: Exception) {
                    null
                }
            } ?: Book(
                title = "Data Tidak Ditemukan",
                author = "-",
                firstPublishYear = "-",
                editionCount = "-",
                language = "-"
            )

            DetailScreen(
                book = book,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}