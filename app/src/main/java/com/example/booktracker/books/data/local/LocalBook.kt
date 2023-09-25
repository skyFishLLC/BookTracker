package com.example.booktracker.books.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class LocalBook(
    @PrimaryKey
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    val title: String,
    @ColumnInfo(name = "r_author")
    val author: String,
    @ColumnInfo(name = "r_genre")
    val genre: String,
    @ColumnInfo(name = "r_series")
    val series: String?,
    @ColumnInfo(name = "r_finished")
    var finished: Boolean = false
)