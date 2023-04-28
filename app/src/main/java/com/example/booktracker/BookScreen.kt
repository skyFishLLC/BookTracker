package com.example.booktracker


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookScreen(){
    val viewModel: BookTrackerViewModel = viewModel()

    LazyColumn(
       contentPadding = PaddingValues(
           vertical = 8.dp,
           horizontal = 6.dp
       )
    ){
        items(viewModel.state.value){ book ->
            BookItem(book){ id ->
                viewModel.toggleFinished(id)
            }
        }
    }

}

@Composable
fun BookItem(book: Book,
            onClick: (id: Int) -> Unit
){
    val icon = if(book.finished) Icons.Default.Check else Icons.Default.Clear
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .padding(8.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ){
            FinishedIcon(icon,
                Modifier
                    .weight(0.15f)
            ){ onClick(book.id) }
            BookDetails(
                book.title,
                book.author,
                Modifier
                    .weight(0.85f)
            )
        }
    }
}

@Composable
private fun BookDetails(title: String, author: String, modifier: Modifier){
    Column(modifier = modifier){
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = author,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun FinishedIcon(
    icon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit
){

    Image(
        imageVector = icon,
        contentDescription = "Book Icon",
        modifier = modifier
            .padding(6.dp)
            .clickable { onClick() }
    )
}























