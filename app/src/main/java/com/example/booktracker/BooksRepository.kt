package com.example.booktracker

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class BooksRepository {
    private var api: BooksApi = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
        .build()
        .create(BooksApi::class.java)
    private var booksDao = BooksDb.getDaoInstance(BookApplication.getAppContext())

    suspend fun getAllBooks(): List<Book>{
        return withContext(Dispatchers.IO){
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

    suspend fun toggleFinishedDb(id: Int, oldValue: Boolean) =
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