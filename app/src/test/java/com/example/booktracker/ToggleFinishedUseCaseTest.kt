package com.example.booktracker

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.usecase.GetSortedBooksFromCacheUseCase
import com.example.booktracker.books.domain.usecase.ToggleFinishedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleFinishedUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toggleBook_finishedField() = scope.runTest{
        val repo = BooksRepository(
            api = FakeBooksApi(),
            booksDao = FakeBooksDao(),
            dispatcher = dispatcher
        )
        val getSortedBooksFromCacheUseCase = GetSortedBooksFromCacheUseCase(repo)
        val useCaseUnderTest = ToggleFinishedUseCase(
            repo = repo,
            getSortedBooksFromCacheUseCase = getSortedBooksFromCacheUseCase
        )
        repo.loadBooks()
        advanceUntilIdle()

        val books = MockData.getDomainBooks().toMutableList()
        val target = books[0]
        val finished = target.finished
        val updatedBooks = useCaseUnderTest (
            target.id,
            finished
        )
        advanceUntilIdle()

        books[0] = target.copy(finished = !target.finished)
        assert(updatedBooks == books)
    }
}