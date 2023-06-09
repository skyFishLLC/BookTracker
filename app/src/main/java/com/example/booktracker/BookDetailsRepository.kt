package com.example.booktracker

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class BookDetailsRepository {
    private var api: BooksApi = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
        .build()
        .create(BooksApi::class.java)
    private var booksDao = BooksDb.getDaoInstance(BookApplication.getAppContext())

    private suspend fun refreshCache(id: Int){
        val mapWrapper = api.getBook(id)
        val remoteBook = mapWrapper.values.first()
        val bookFinished = booksDao.getBook(id).finished
        booksDao.add(remoteBook)
        if(bookFinished){
            val partialBook = PartialBook_finished(id, true)
            booksDao.update(partialBook)
        }
    }

    suspend fun getSingleBook(id: Int): Book{
        return withContext(Dispatchers.IO){
            try {
                refreshCache(id)
            }catch (e: Exception){
                when(e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        Log.e("BooksViewModel","Error: No data to display")
                        return@withContext booksDao.getBook(id)
                    }else -> throw e
                }
            }
            return@withContext booksDao.getBook(id)
        }
    }
}