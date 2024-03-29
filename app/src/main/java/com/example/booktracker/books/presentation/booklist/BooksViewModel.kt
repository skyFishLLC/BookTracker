package com.example.booktracker.books.presentation.booklist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.books.data.di.MainDispatcher
import com.example.booktracker.books.domain.usecase.GetInitialBookListFromNetUseCase
import com.example.booktracker.books.domain.usecase.ToggleFinishedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val toggleFinishedUseCase: ToggleFinishedUseCase,
    private val getBooksUseCase: GetInitialBookListFromNetUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

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
        viewModelScope.launch(errorHandler + dispatcher) {
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
        viewModelScope.launch(errorHandler + dispatcher) {
            val updatedBooks = toggleFinishedUseCase(id, book.finished)
            _state.value = _state.value.copy(books = updatedBooks)
        }
    }
}










