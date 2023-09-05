package com.example.booktracker.books.data.mapper

import com.example.booktracker.books.data.local.LocalBook
import com.example.booktracker.books.data.remote.RemoteBook
import com.example.booktracker.books.domain.model.Book

fun Book.toRemoteBook(): RemoteBook {
    return RemoteBook(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series
    )
}

fun Book.toLocalBook(): LocalBook {
    return LocalBook(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series,
        finished = finished
    )
}

fun LocalBook.toBook(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series,
        finished = finished
    )
}

fun LocalBook.toRemoteBook(): RemoteBook {
    return RemoteBook(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series
    )
}

fun RemoteBook.toLocalBook(): LocalBook {
    return LocalBook(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series
    )
}

fun RemoteBook.Book(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        genre = genre,
        series = series
    )
}

fun List<LocalBook>.toBookList(): List<Book>{
    return this.map{
        Book(
            id = it.id,
            title = it.title,
            author = it.author,
            genre = it.genre,
            series = it.series,
            finished = it.finished
        )
    }
}

fun List<RemoteBook>.toLocalBookList(): List<LocalBook>{
    return this.map{
        LocalBook(
            id = it.id,
            title = it.title,
            author = it.author,
            genre = it.genre,
            series = it.series,
        )
    }
}
