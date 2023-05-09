package com.example.booktracker

import androidx.room.*

@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    suspend fun getAll(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(books: List<Book>)

    @Update(entity = Book::class)
    suspend fun update(partialBook: PartialBook_finished)
}