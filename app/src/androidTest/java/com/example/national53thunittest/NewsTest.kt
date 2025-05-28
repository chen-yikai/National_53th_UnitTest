package com.example.national53thunittest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NewsTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        signUp(rule)
        signIn(rule)
        rule.onNodeWithTag("open_drawer").performClick()
        rule.onNodeWithText("最新消息").performClick()
    }

    val totalNews =
        rule.onAllNodesWithTag("news_card", useUnmergedTree = true)

    @Test
    fun `1 List view show all details`() {
        val detailNames = listOf<String>("title", "organizer", "publish_date", "view_count")

        detailNames.forEach {
            assertEquals(
                totalNews.fetchSemanticsNodes().size,
                rule.onAllNodesWithTag(it, useUnmergedTree = true).fetchSemanticsNodes().size
            )
        }
    }

    @Test
    fun `2`() {

    }

    @Test
    fun `3`() {

    }

    @Test
    fun `4 Click list to nav to news_details`() {
        rule.onAllNodesWithTag("news_card")[0].performClick()
        rule.onNodeWithTag("news_details_screen").assertExists()
    }

    @Test
    fun `5 Search news`() {
        rule.onNodeWithTag("search_news").performTextInput("航太")
        rule.onNodeWithTag("search_button").performClick()
        val searchResult = rule.onAllNodesWithText("航太", useUnmergedTree = true)
        assertEquals(true, searchResult.fetchSemanticsNodes().size > 1)
    }
}