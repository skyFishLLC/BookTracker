package com.example.booktracker

class GetSortedBooksFromCacheUseCase {
    private val repo: BooksRepository = BooksRepository()
    suspend operator fun invoke(): List<Book>{
        return repo.getCachedBooks().sortedBy { it.title }
    }
}