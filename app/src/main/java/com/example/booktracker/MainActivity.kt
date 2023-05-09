package com.example.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            BooksScreen(){ id ->
                navController.navigate("books/$id")
            }
        }

        composable(
                route = "books/{book_id}",
                arguments = listOf(navArgument("book_id"){
                    type = NavType.IntType
                })
            ){
            BookDetailsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookTrackerTheme {
        BooksScreen()
    }
}