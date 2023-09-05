package com.example.booktracker.books.presentation.booklist

import com.example.booktracker.books.domain.model.Book

data class BooksScreenState(
    val books: List<Book>,
    val isLoading: Boolean,
    val error: String? = null
)
