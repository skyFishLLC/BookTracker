package com.example.booktracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BookTrackerViewModel(): ViewModel() {
    val state = mutableStateOf(mockBookList)
    fun toggleFinished(id: Int){
        val books = state.value.toMutableList()
        val bookIndex = books.indexOfFirst { it.id == id }
        val book = books[bookIndex]
        books[bookIndex] = book.copy(finished = !book.finished)
        state.value = books
    }
}