package com.example.booktracker

data class BooksScreenState(
    val books: List<Book>,
    val isLoading: Boolean,
    val error: String? = null
)
