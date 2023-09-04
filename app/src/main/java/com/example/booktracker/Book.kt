package com.example.booktracker

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val series: String?,
    val finished: Boolean = false
)