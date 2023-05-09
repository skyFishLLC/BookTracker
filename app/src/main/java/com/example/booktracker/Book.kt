package com.example.booktracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "r_id")
    @SerializedName("r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    @SerializedName("r_title")
    val title: String,
    @ColumnInfo(name = "r_author")
    @SerializedName("r_author")
    val author: String,
    @ColumnInfo(name = "r_genre")
    @SerializedName("r_genre")
    val genre: String,
    @ColumnInfo(name = "r_series")
    @SerializedName("r_series")
    val series: String?,
    @ColumnInfo(name = "r_finished")
    @SerializedName("r_finished")
    val finished: Boolean = false
)