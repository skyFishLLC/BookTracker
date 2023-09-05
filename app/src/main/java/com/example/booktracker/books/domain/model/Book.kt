package com.example.booktracker.books.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val series: String?,
    val finished: Boolean = false
)