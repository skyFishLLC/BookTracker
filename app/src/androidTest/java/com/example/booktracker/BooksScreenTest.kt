package com.example.booktracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.booktracker.books.presentation.ContentDescriptions
import com.example.booktracker.books.presentation.booklist.BooksScreen
import com.example.booktracker.books.presentation.booklist.BooksScreenState
import com.example.booktracker.ui.theme.BookTrackerTheme
import org.junit.Rule
import org.junit.Test

class BooksScreenTest {
    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered(){
        testRule.setContent {
            BookTrackerTheme {
                BooksScreen(
                    state = BooksScreenState(
                        books = emptyList(),
                        isLoading = true
                    ),
                    onFinishedClick = {}
                )
            }
        }
        testRule.onNodeWithContentDescription(
            ContentDescriptions.LOADING_INDICATOR
        ).assertIsDisplayed()
    }

    @Test
    fun contentLoadedState_isRendered(){
        val books = MockData.getDomainBooks()
        testRule.setContent {
            BookTrackerTheme {
                BooksScreen(
                    state = BooksScreenState(
                        books = books,
                        isLoading = false
                    ),
                    onFinishedClick = {}
                )
            }
        }
        testRule.onNodeWithText(books[0].title).assertIsDisplayed()
        testRule.onNodeWithText(books[0].author).assertIsDisplayed()
        testRule.onNodeWithContentDescription(
            ContentDescriptions.LOADING_INDICATOR
        ).assertDoesNotExist()
    }

    @Test
    fun contentLoadedState_ClickOnBook_isRegistered(){
        val books = MockData.getDomainBooks()
        val target = books[0]
        testRule.setContent {
            BookTrackerTheme {
                BooksScreen(
                    state = BooksScreenState(
                        books = books,
                        isLoading = false
                    ),
                    onFinishedClick = {},
                    onItemClick = {id ->
                        assert(id == target.id)
                    }
                )
            }
        }
        testRule.onNodeWithText(target.title).performClick()
    }

}









