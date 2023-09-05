package com.example.booktracker.books.presentation.booklist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.books.domain.usecase.GetInitialBookListFromNetUseCase
import com.example.booktracker.books.domain.usecase.ToggleFinishedUseCase
import kotlinx.coroutines.*

class BooksViewModel(): ViewModel() {
    private val toggleFinishedUseCase = ToggleFinishedUseCase()
    private val getBooksUseCase = GetInitialBookListFromNetUseCase()

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
            val books = getBooksUseCase()
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
        viewModelScope.launch {
            val updatedBooks = toggleFinishedUseCase(id, book.finished)
            _state.value = _state.value.copy(books = updatedBooks)
        }
    }
}










