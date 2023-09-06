package com.example.booktracker.books.domain.usecase

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.model.Book
import javax.inject.Inject

class ToggleFinishedUseCase @Inject constructor(
    private val repo: BooksRepository,
    private val getSortedBooksFromCacheUseCase: GetSortedBooksFromCacheUseCase
) {
    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean
    ): List<Book> {
        val newFinished = oldValue.not()
        repo.toggleFinishedDb(id, newFinished)
        return getSortedBooksFromCacheUseCase()
    }
}