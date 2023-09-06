package com.example.booktracker.books.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalBook::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDb: RoomDatabase() {
    abstract val dao: BooksDao
}