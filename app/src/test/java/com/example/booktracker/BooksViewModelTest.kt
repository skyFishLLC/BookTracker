package com.example.booktracker

import com.example.booktracker.books.data.repo.BooksRepository
import com.example.booktracker.books.domain.usecase.GetInitialBookListFromNetUseCase
import com.example.booktracker.books.domain.usecase.GetSortedBooksFromCacheUseCase
import com.example.booktracker.books.domain.usecase.ToggleFinishedUseCase
import com.example.booktracker.books.presentation.booklist.BooksScreenState
import com.example.booktracker.books.presentation.booklist.BooksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BooksViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private fun getViewModel(): BooksViewModel{
        val repo = BooksRepository(
            api = FakeBooksApi(),
            booksDao = FakeBooksDao(),
            dispatcher = dispatcher
        )

        return BooksViewModel(
            toggleFinishedUseCase = ToggleFinishedUseCase(
                getSortedBooksFromCacheUseCase = GetSortedBooksFromCacheUseCase(
                    repo = repo,
                ),
                repo = repo,
            ),
            getBooksUseCase = GetInitialBookListFromNetUseCase(
                repo = repo,
                getSortedBooksFromCacheUseCase = GetSortedBooksFromCacheUseCase(
                    repo = repo
                )
            ),
            dispatcher = dispatcher
        )

    }

    @Test
    fun initialState_isProduced() = scope.runTest{
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == BooksScreenState(
                books = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }


    @Test
    fun contentLoadedState_isProduced() = scope.runTest {
        val testViewModel = getViewModel()
        advanceUntilIdle()
        val currentState = testViewModel.state.value
        assert(
            currentState == BooksScreenState(
                books = MockData.getDomainBooks(),
                isLoading = false,
                error = null
            )
        )

    }
}










