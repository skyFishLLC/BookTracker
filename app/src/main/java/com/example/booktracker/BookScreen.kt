package com.example.booktracker


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookScreen(){
    LazyColumn(
       contentPadding = PaddingValues(
           vertical = 8.dp,
           horizontal = 6.dp
       )
    ){
        items(mockBookList){ book ->
            BookItem(book)
        }
    }

}

@Composable
fun BookItem(book: Book){
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
            BookIcon(
                Icons.Default.Check,
                Modifier
                    .weight(0.15f)
            )
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
private fun BookIcon(icon: ImageVector, modifier: Modifier){
    Image(
        imageVector = icon,
        contentDescription = "Book Icon",
        modifier = Modifier
            .padding(6.dp)
    )
}







data class Book(
    val id: Int,
    val title: String,
    val author: String
)

val mockBookList = listOf(
    Book(0,"The Fellowship of the Ring","J. R. R. Tolkien"),
    Book(1,"The Two Towers","J. R. R. Tolkien"),
    Book(2,"The Return of the King","J. R. R. Tolkien"),
    Book(3,"The Hobbit","J. R. R. Tolkien"),
    Book(4,"The Eye of the World","Robert Jordan"),
    Book(5,"The Dragon Reborn","Robert Jordan"),
    Book(6,"A Christmas Carol","Charles Dickens"),
    Book(7,"A Tale of Two Cities","Charles Dickens"),
    Book(8,"War and Peace","Leo Tolstoy"),
    Book(9,"Moby Dick","Herman Melville"),
    Book(10,"Tom Sawyer","Mark Twain"),
    Book(11,"Alice in Wonderland","Lewis Carroll"),
    Book(12,"Jane Eyre","Charlotte Bronte"),
    Book(13,"David Copperfield","Charles Dickens"),
    Book(14,"The Lion, the Witch, and the Wardrobe","C. S. Lewis"),
    Book(15, "Getting There", "Samir Deokuliar")
)

















