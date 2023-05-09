package com.example.booktracker

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class BookDetailsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    private var api: BooksApi
    private var booksDao = BooksDb.getDaoInstance(BookApplication.getAppContext())
    val state = mutableStateOf<Book?>(null)
    private val errorHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
            .build()
        api = retrofit.create(BooksApi::class.java)
        val id = stateHandle.get<Int>("book_id") ?: 0
        getBook(id)
    }

    private suspend fun refreshCache(id: Int){
        val remoteBook = getRemoteBook(id)
        val bookFinished = booksDao.getBook(id).finished
        booksDao.add(remoteBook)
        if(bookFinished){
            val partialBook = PartialBook_finished(id, true)
            booksDao.update(partialBook)
        }
    }

    private fun getBook(id: Int){
        viewModelScope.launch(errorHandler){
            try {
                refreshCache(id)
            }catch (e: Exception){
                when(e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        Log.e("BooksViewModel","Error: No data to display")
                        state.value = booksDao.getBook(id)
                    }else -> throw e
                }
            }
            state.value = booksDao.getBook(id)
        }
    }

    private suspend fun getRemoteBook(id: Int): Book {
        return withContext(Dispatchers.IO){
            val mapWrapper = api.getBook(id)
            return@withContext mapWrapper.values.first()
        }
    }


}