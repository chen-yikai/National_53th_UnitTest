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
class ChangePassword {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val errors: Error? = getError(context, "ChangePasswordPage", "test")

    val newPassword = rule.onNodeWithTag("new_password")
    val checkNewPassword = rule.onNodeWithTag("check_new_password")
    val submitButton = rule.onNodeWithTag("submit_button")

    fun submitForm(test: () -> Unit) {
        submitButton.performClick()
        test()
        submitButton.performClick()
    }

    fun setUp() {
        signUp(rule)
        signIn(rule)
        rule.onNodeWithTag("nav_to_profile").assertExists().performClick()
        rule.onNodeWithTag("change_password").assertExists().performClick()
    }

    @Test
    fun `1 Wrong password format show error`() {
        setUp()
        newPassword.performTextInput("abc123456")
        submitForm {
            rule.onNodeWithText(errors?.errorMeg3_2.toString()).assertExists()
        }
    }

    @Test
    fun `2 Password and CheckPassword not the same`() {
        setUp()
        newPassword.performTextInput("Abc123456")
        checkNewPassword.performTextInput("Abc1234567")
        submitForm {
            rule.onNodeWithText(errors?.errorMeg3_1!!).assertExists()
        }
    }

    @Test
    fun `3 Nav to profile after submit`() {
        setUp()
        newPassword.performTextInput("Abc1234567")
        checkNewPassword.performTextInput("Abc1234567")
        submitButton.performClick()
        rule.onNodeWithTag("profile_screen").assertExists()
    }

    @Test
    fun `4 SignOut enter old password will failed signIn`() {
        val emailInput = rule.onNodeWithTag("emailInput")
        val passwordInput = rule.onNodeWithTag("passwordInput")
        val loginButton = rule.onNodeWithTag("login")

        emailInput.performTextInput("example@example.com")
        passwordInput.performTextInput("Abc123456")
        loginButton.performClick()

        rule.onNodeWithText("News").assertDoesNotExist()
    }

    @Test
    fun `5 SignOut enter new password will successfully signIn`() {
        val emailInput = rule.onNodeWithTag("emailInput")
        val passwordInput = rule.onNodeWithTag("passwordInput")
        val loginButton = rule.onNodeWithTag("login")

        emailInput.performTextInput("example@example.com")
        passwordInput.performTextInput("Abc1234567")
        loginButton.performClick()

        rule.onNodeWithText("News").assertExists()
    }
}
