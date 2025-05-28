package com.example.national53thunittest

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Profile {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        Utils.signIn(rule)
        rule.onNodeWithTag("nav_to_profile").performClick()
    }

    @Test
    fun `1 User name`() {
        rule.onNodeWithTag("姓名").assertTextEquals("user")
    }

    @Test
    fun `2 Email`() {
        rule.onNodeWithTag("Email").assertTextEquals("example@example.com")
    }

    @Test
    fun `3 Click change password`() {
        rule.onNodeWithTag("change_password").performClick()
        rule.onNodeWithTag("change_password_dialog").assertExists()
    }

    @Test
    fun `4 Clock SignOut`() {
        rule.onNodeWithTag("sign_out").performClick()
        rule.onNodeWithTag("SignInScreen").assertExists()
    }
}