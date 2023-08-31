package com.example.booktracker

class GetInitialBookListFromNetUseCase {
    private val repo: BooksRepository = BooksRepository()
    private val getSortedBooksFromCacheUseCase = GetSortedBooksFromCacheUseCase()
    suspend operator fun invoke(): List<Book>{
        repo.loadBooks()
        return getSortedBooksFromCacheUseCase()
    }
}