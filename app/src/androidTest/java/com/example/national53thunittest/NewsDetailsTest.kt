package com.example.national53thunittest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NewsDetailsTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        performSignUp(rule)
        performSignIn(rule)
        rule.onNodeWithTag("open_drawer").performClick()
        rule.onNodeWithText("最新消息").performClick()
        rule.onAllNodesWithTag("news_card").onFirst().performClick()
    }

    @Test
    fun `1 Show title`() {
        rule.onNodeWithTag("title").assertExists()
    }

    @Test
    fun `2 Show content`() {
        rule.onNodeWithTag("content").assertExists()
    }

    @Test
    fun `3 Show publishDate`() {
        rule.onNodeWithTag("publishDate").assertExists()
    }

    @Test
    fun `4 Show organizer`() {
        rule.onNodeWithTag("organizer").assertExists()
    }

    @Test
    fun `5 Click back button nav to News screen`() {
        rule.onNodeWithTag("back_to_news").performClick()
        rule.onNodeWithTag("news_screen").assertExists()
    }
}