package com.example.booktracker.books.domain.usecase

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.model.Book

class ToggleFinishedUseCase {
    private val repo: BooksRepository = BooksRepository()
    private val getSortedBooksFromCacheUseCase = GetSortedBooksFromCacheUseCase()
    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean
    ): List<Book> {
        val newFinished = oldValue.not()
        repo.toggleFinishedDb(id, newFinished)
        return getSortedBooksFromCacheUseCase()
    }
}