package com.example.national53thunittest

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SignInTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    val formatError = getError(context, "LoginPage", "test")
    val emailInput = rule.onNodeWithTag("emailInput")
    val passwordInput = rule.onNodeWithTag("passwordInput")
    val loginButton = rule.onNodeWithTag("login")
    val sureButton = rule.onNodeWithText("確定")

    fun submitForm(meanWhiled: () -> Unit) {
        loginButton.performClick()
        meanWhiled()
        sureButton.performClick()
    }

    @Before
    fun setUp() {
        performSignUp(rule)
    }

    @Test
    fun `1 Show Login page after launch`() {
        rule.onNodeWithTag("SignInScreen").assertExists()
    }

    @Test
    fun `2 Email wrong format`() {
        emailInput.performTextInput("test.test")
        submitForm {
            rule.onNodeWithText(formatError?.errorMeg1_1.toString()).assertExists()
        }
    }

    @Test
    fun `4 Wrong password`() {
        emailInput.assertExists().performTextInput("example@example.com")
        passwordInput.assertExists().performTextInput("WrongPW")
        submitForm {
            rule.onNodeWithText(formatError?.errorMeg1_2!!).assertExists()
        }
    }

    @Test
    fun `5 Wrong user`() {
        emailInput.performTextInput("example@unknow.com")
        passwordInput.performTextInput("Abc123456")
        submitForm {
            rule.onNodeWithText(formatError?.errorMeg1_2!!).assertExists()
        }
    }

    @Test
    fun `3 Sign In`() {
        emailInput.performTextInput("example@example.com")
        passwordInput.performTextInput("Abc123456")

        rule.onNodeWithText("登入").performClick()
        rule.onNodeWithText("News").assertExists()
    }
}
