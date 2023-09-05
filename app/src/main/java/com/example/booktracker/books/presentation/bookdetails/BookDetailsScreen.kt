package com.example.booktracker.books.presentation.bookdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booktracker.books.presentation.booklist.BookDetails
import com.example.booktracker.books.presentation.booklist.FinishedIcon

@Composable
fun BookDetailsScreen(state: BookDetailsScreenState){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.book != null){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val icon = if(state.book.finished) Icons.Default.Check else Icons.Default.Clear

                FinishedIcon(
                    icon = icon,
                    modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                    onClick = {}
                )
                BookDetails(
                    author = state.book.author,
                    title = state.book.title,
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                AdditionalDetails(
                    genre = state.book.genre,
                    series = state.book.series
                )
            }
        }
        if(state.isLoading){
            CircularProgressIndicator()
        }
        if(state.error != null){
            Text(
                text = state.error,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun AdditionalDetails(
    genre: String,
    series: String?,
    modifier: Modifier = Modifier.padding(bottom = 32.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
){
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = genre,
            fontSize = 20.sp
        )
        Text(
            text = series ?: "No Series",
            fontSize = 20.sp
        )
    }
}










