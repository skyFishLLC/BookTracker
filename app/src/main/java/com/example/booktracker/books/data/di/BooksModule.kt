package com.example.booktracker.books.data.di

import android.content.Context
import androidx.room.Room
import com.example.booktracker.books.data.local.BooksDao
import com.example.booktracker.books.data.local.BooksDb
import com.example.booktracker.books.data.remote.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BooksModule {
    @Provides
    fun providesRoomDao(database: BooksDb): BooksDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): BooksDb {
        return Room.databaseBuilder(
            appContext.applicationContext,
            BooksDb::class.java,
            "books_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): BooksApi {
        return retrofit.create(BooksApi::class.java)
    }
}