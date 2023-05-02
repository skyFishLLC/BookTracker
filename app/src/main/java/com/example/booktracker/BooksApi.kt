package com.example.booktracker

import retrofit2.Call
import retrofit2.http.GET

interface BooksApi {
    @GET("books.json")
    suspend fun getBooks(): List<Book>
}