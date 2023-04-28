package com.example.booktracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookTrackerViewModel(): ViewModel() {
    private var api: BooksApi
    private lateinit var booksCall: Call<List<Book>>
    val state = mutableStateOf(emptyList<Book>())

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

    private fun getBooks(){
        booksCall = api.getBooks()
        booksCall.enqueue(
            object : Callback<List<Book>>{
                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                    response.body()?.let { books -> state.value = books }
                }
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        booksCall.cancel()
    }

    fun toggleFinished(id: Int){
        val books = state.value.toMutableList()
        val bookIndex = books.indexOfFirst { it.id == id }
        val book = books[bookIndex]
        books[bookIndex] = book.copy(finished = !book.finished)
        state.value = books
    }
}