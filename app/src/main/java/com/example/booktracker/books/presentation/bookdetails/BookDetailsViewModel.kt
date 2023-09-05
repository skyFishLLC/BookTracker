package com.example.booktracker.books.presentation.bookdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.books.data.repo.BookDetailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    private val repo = BookDetailsRepository()

    val state: State<BookDetailsScreenState>
        get() = _state
    private val _state = mutableStateOf(
        BookDetailsScreenState(
            book = null,
            isLoading = true
        )
    )
    private val errorHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
    }

    init {
        val id = stateHandle.get<Int>("book_id") ?: 0
        getBook(id)
    }

    private fun getBook(id: Int){
        viewModelScope.launch(errorHandler) {
            val book = repo.getSingleBook(id)
            _state.value = _state.value.copy(
                book = book,
                isLoading = false
            )
        }
    }




}