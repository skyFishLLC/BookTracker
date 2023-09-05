package com.example.booktracker.books.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booktracker.books.presentation.bookdetails.BookDetailsScreen
import com.example.booktracker.books.presentation.bookdetails.BookDetailsViewModel
import com.example.booktracker.books.presentation.booklist.BooksScreen
import com.example.booktracker.books.presentation.booklist.BooksScreenState
import com.example.booktracker.books.presentation.booklist.BooksViewModel
import com.example.booktracker.ui.theme.BookTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTrackerTheme {
                BookTrackerApp()
            }
        }
    }
}

@Composable
private fun BookTrackerApp(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "books"){
        composable(route = "books"){
            val viewModel: BooksViewModel = viewModel()
            BooksScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("books/$id")
                },
                onFinishedClick = {id ->
                    viewModel.toggleFinished(id)
                }
            )
        }

        composable(
                route = "books/{book_id}",
                arguments = listOf(navArgument("book_id"){
                    type = NavType.IntType
                })
            ){
            val viewModel: BookDetailsViewModel = viewModel()
            BookDetailsScreen(viewModel.state.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookTrackerTheme {
        BooksScreen(
            BooksScreenState(listOf(),false),
            {},
            {_,->}
        )
    }
}