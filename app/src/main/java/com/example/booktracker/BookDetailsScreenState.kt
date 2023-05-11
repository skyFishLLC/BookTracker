package com.example.booktracker

data class BookDetailsScreenState(
    val book: Book?,
    val isLoading: Boolean,
    val error: String? = null
)
