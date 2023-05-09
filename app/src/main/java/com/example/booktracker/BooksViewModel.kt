package com.example.booktracker

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class BooksViewModel(): ViewModel() {
    private var api: BooksApi
    private var booksDao = BooksDb.getDaoInstance(BookApplication.getAppContext())
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
            val books = getAllBooks()
            state.value = books
        }
    }

    private suspend fun getAllBooks(): List<Book>{
        return withContext(Dispatchers.IO){
            try {
                refreshCache()
            }catch (e: Exception){
                when(e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                            Log.e("BooksViewModel","Error: No data to display")
                            return@withContext booksDao.getAll()
                        }else -> throw e
                }
            }
            return@withContext booksDao.getAll()
        }
    }

    private suspend fun refreshCache(){
        val remoteBooks = api.getBooks()
        val finishedBooks = booksDao.getAllFinished()
        booksDao.addAll(remoteBooks)
        booksDao.updateAll(
            finishedBooks.map{
                PartialBook_finished(it.id,true)
            }
        )
    }

    fun toggleFinished(id: Int){
        val books = state.value.toMutableList()
        val bookIndex = books.indexOfFirst { it.id == id }
        val book = books[bookIndex]
        books[bookIndex] = book.copy(finished = !book.finished)
        viewModelScope.launch {
            val updatedBooks = toggleFinishedDb(id, book.finished)
            state.value = updatedBooks
        }
    }

    private suspend fun toggleFinishedDb(id: Int, oldValue: Boolean) =
        withContext(Dispatchers.IO){
            booksDao.update(
                PartialBook_finished(
                    id = id,
                    finished = !oldValue
                )
            )
            booksDao.getAll()
        }



}










