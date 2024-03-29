package com.example.booktracker.books.data.repo

import android.util.Log
import com.example.booktracker.BookApplication
import com.example.booktracker.books.data.di.IoDispatcher
import com.example.booktracker.books.data.local.BooksDao
import com.example.booktracker.books.data.remote.BooksApi
import com.example.booktracker.books.data.local.BooksDb
import com.example.booktracker.books.data.local.PartialLocalBook_finished
import com.example.booktracker.books.data.mapper.toBookList
import com.example.booktracker.books.data.mapper.toLocalBookList
import com.example.booktracker.books.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepository @Inject constructor(
    private val api: BooksApi,
    private val booksDao: BooksDao,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {

    suspend fun loadBooks(){
        return withContext(dispatcher){
            try {
                refreshCache()
            }catch (e: Exception){
                when(e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        Log.e("BooksViewModel","Error: No data from Firebase")
                        if(booksDao.getAll().isEmpty()){
                            Log.e("BooksViewModel","Error: No data from local cache")
                            throw Exception("Error: Device offline and\nno data from local cache")
                        }
                    }else -> throw e
                }
            }
        }
    }

    suspend fun getCachedBooks(): List<Book>{
        return withContext(dispatcher){
            return@withContext booksDao.getAll().toBookList()
        }
    }

    private suspend fun refreshCache(){
        val remoteBooks = api.getBooks()
        val finishedBooks = booksDao.getAllFinished()
        booksDao.addAll(remoteBooks.toLocalBookList())
        booksDao.updateAll(
            finishedBooks.map{
                PartialLocalBook_finished(it.id,true)
            }
        )
    }

    suspend fun toggleFinishedDb(id: Int, value: Boolean) =
        withContext(dispatcher){
            booksDao.update(
                PartialLocalBook_finished(
                    id = id,
                    finished = value
                )
            )
        }

}