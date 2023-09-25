package com.example.booktracker

import com.example.booktracker.books.data.remote.BooksApi
import com.example.booktracker.books.data.remote.RemoteBook
import kotlinx.coroutines.delay

class FakeBooksApi: BooksApi {
    override suspend fun getBooks(): List<RemoteBook> {
        delay(500)
        return MockData.getRemoteBooks()
    }

    override suspend fun getBook(id: Int): Map<String, RemoteBook> {
        TODO("Not yet implemented")
    }

}