package com.example.booktracker.books.domain.usecase

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.model.Book
import javax.inject.Inject

class GetSortedBooksFromCacheUseCase @Inject constructor(
    private val repo: BooksRepository
) {
    suspend operator fun invoke(): List<Book>{
        return repo.getCachedBooks().sortedBy { it.title }
    }
}