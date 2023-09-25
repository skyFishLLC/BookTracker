package com.example.booktracker

import com.example.booktracker.books.data.local.BooksDao
import com.example.booktracker.books.data.local.LocalBook
import com.example.booktracker.books.data.local.PartialLocalBook_finished
import kotlinx.coroutines.delay

class FakeBooksDao: BooksDao {

    private var books = MockData.getLocalBooks()

    private fun updateBook(partialBook: PartialLocalBook_finished){
        val id = partialBook.id
        val book = this.books[id]
        if(book != null){
            this.books[id].finished = partialBook.finished
        }
    }

    override suspend fun getAll(): List<LocalBook> {
        delay(500)
        return books
    }

    override suspend fun addAll(books: List<LocalBook>) {
        this.books = books
    }

    override suspend fun update(partialBook: PartialLocalBook_finished) {
        delay(500)
        updateBook(partialBook)
    }

    override suspend fun updateAll(partialBooks: List<PartialLocalBook_finished>) {
        delay(500)
        partialBooks.forEach {partialBook ->
            updateBook(partialBook)
        }
    }

    override suspend fun getAllFinished(): List<LocalBook> {
        return books.filter {it.finished}
    }

    override suspend fun getBook(id: Int): LocalBook {
        TODO("Not yet implemented")
    }

    override suspend fun add(book: LocalBook) {
        TODO("Not yet implemented")
    }
}