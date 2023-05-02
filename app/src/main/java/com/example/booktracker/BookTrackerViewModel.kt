package com.example.booktracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookTrackerViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    private var api: BooksApi
    val state = mutableStateOf(emptyList<Book>())
    private val errorHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
    }

    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
            .build()
        api = retrofit.create(BooksApi::class.java)
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch(errorHandler) {
            val books = getRemoteBooks()
            state.value = books.restoreFinishedField()
        }
    }

    private suspend fun getRemoteBooks(): List<Book>{
        return withContext(Dispatchers.IO){
            api.getBooks()
        }
    }

    private fun List<Book>.restoreFinishedField(): List<Book> {
        stateHandle.get<List<Int>?>("finished")?.let {selectedIds ->
            val booksMap = this.associateBy { it.id }
            selectedIds.forEach {id ->
                booksMap[id]?.finished = true
            }
            return booksMap.values.toList()
        }
        return this
    }

    fun toggleFinished(id: Int){
        val books = state.value.toMutableList()
        val bookIndex = books.indexOfFirst { it.id == id }
        val book = books[bookIndex]
        books[bookIndex] = book.copy(finished = !book.finished)
        state.value = books
    }
}