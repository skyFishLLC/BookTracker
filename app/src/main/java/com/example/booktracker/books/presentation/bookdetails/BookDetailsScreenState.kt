package com.example.booktracker.books.presentation.bookdetails

import com.example.booktracker.books.domain.model.Book

data class BookDetailsScreenState(
    val book: Book?,
    val isLoading: Boolean,
    val error: String? = null
)
