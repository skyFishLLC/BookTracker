package com.example.booktracker.books.data.remote

import com.example.booktracker.books.data.remote.RemoteBook
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("books.json")
    suspend fun getBooks(): List<RemoteBook>

    @GET("books.json?orderBy=\"r_id\"")
    suspend fun getBook(@Query("equalTo") id: Int): Map<String, RemoteBook>
}