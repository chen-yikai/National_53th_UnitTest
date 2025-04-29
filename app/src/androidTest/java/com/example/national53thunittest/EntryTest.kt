package com.example.national53thunittest

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class EntryTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun `Show Login page`() {
        rule.onNodeWithTag("SignInScreen").assertExists()
    }

    @Test
    fun `Check Login Input Fields`() {
        val formatError = getError(context, "LoginPage", "dev")
        val emailInput = rule.onNodeWithTag("emailInput")
        val passwordInput = rule.onNodeWithTag("passwordInput")
        val loginButton = rule.onNodeWithTag("login")
        val sureButton = rule.onNodeWithText("確定")

        emailInput.assertExists().performTextInput("test.test")
        loginButton.performClick()
        rule.onNodeWithText(formatError?.errorMeg1_1!!)
            .assertExists()

        Thread.sleep(2000)

        emailInput.performTextClearance()
        sureButton.assertExists().performClick()

        emailInput.assertExists().performTextInput("fake@example.com")
        passwordInput.assertExists().performTextInput("fake123456")
        loginButton.performClick()
        rule.onNodeWithText(formatError.errorMeg1_2!!).assertExists()

        sureButton.assertExists().performClick()

        Thread.sleep(2000)
    }
}