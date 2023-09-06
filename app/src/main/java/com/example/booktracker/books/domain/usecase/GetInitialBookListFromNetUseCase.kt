package com.example.booktracker.books.domain.usecase

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.model.Book
import javax.inject.Inject

class GetInitialBookListFromNetUseCase @Inject constructor(
    private val repo: BooksRepository,
    private val getSortedBooksFromCacheUseCase: GetSortedBooksFromCacheUseCase
) {

    suspend operator fun invoke(): List<Book>{
        repo.loadBooks()
        return getSortedBooksFromCacheUseCase()
    }
}