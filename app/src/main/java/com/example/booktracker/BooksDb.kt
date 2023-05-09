package com.example.booktracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDb: RoomDatabase() {
    abstract val dao: BooksDao

    companion object{
        @Volatile
        private var INSTANCE: BooksDao? = null
        fun getDaoInstance(context: Context): BooksDao{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context): BooksDb = Room.databaseBuilder(
            context.applicationContext,
            BooksDb::class.java,
            "books_database"
        ).fallbackToDestructiveMigration().build()
    }
}