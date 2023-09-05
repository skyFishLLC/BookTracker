package com.example.booktracker.books.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class PartialLocalBook_finished (
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "r_finished")
    val finished: Boolean = false
)