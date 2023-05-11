package com.example.booktracker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class BooksViewModel(): ViewModel() {
    private val repo = BooksRepository()

    val state: State<BooksScreenState>
        get() = _state
    private val _state = mutableStateOf(
        BooksScreenState(
            books = listOf(),
            true
        )
    )
    private val errorHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    init{
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch(errorHandler) {
            val books = repo.getAllBooks()
            _state.value = _state.value.copy(
                books = books,
                isLoading = false
            )
        }
    }

    fun toggleFinished(id: Int){
        val books = _state.value.books.toMutableList()
        val bookIndex = books.indexOfFirst { it.id == id }
        val book = books[bookIndex]
        books[bookIndex] = book.copy(finished = !book.finished)
        viewModelScope.launch {
            val updatedBooks = repo.toggleFinishedDb(id, book.finished)
            _state.value = _state.value.copy(books = updatedBooks)
        }
    }
}










