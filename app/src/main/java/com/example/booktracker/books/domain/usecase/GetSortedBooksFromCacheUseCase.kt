package com.example.booktracker.books.domain.usecase

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.model.Book

class GetSortedBooksFromCacheUseCase {
    private val repo: BooksRepository = BooksRepository()
    suspend operator fun invoke(): List<Book>{
        return repo.getCachedBooks().sortedBy { it.title }
    }
}