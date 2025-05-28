package com.example.national53thunittest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
class HomeTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun Setup() {
        signUp(rule)
        signIn(rule)
    }

    @Test
    fun `1 Nav to profile screen`() {
        rule.onNodeWithTag("nav_to_profile").performClick()
        rule.onNodeWithText("個人資訊").assertExists()
    }

    @Test
    fun `2 Open drawer`() {
        rule.onNodeWithTag("open_drawer").performClick()
        rule.onNodeWithText("最新消息").assertExists()
        rule.onNodeWithTag("close_drawer").performClick()
        rule.onNodeWithTag("drawer").assertDoesNotExist()
    }

    @Test
    fun `3 Five news item`() {
        val items = rule.onAllNodesWithTag("home_news_item")
        assertEquals(5, items.fetchSemanticsNodes().size)
    }

    @Test
    fun `4 Nav to news details screen`() {
        rule.onAllNodesWithTag("home_news_item")[0].performClick()
        rule.onNodeWithTag("news_details_screen").assertExists()
    }
}