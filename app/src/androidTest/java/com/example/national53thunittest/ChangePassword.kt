package com.example.national53thunittest

import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Ignore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dalvik.annotation.TestTarget
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangePassword {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val errors: Error? = getError(context, "ChangePasswordPage", "test")

    val newPassword = rule.onNodeWithTag("new_password")
    val checkNewPassword = rule.onNodeWithTag("check_new_password")
    val submitButton = rule.onNodeWithTag("submit_button")
    val signOut = rule.onNodeWithTag("sign_out", useUnmergedTree = true)

    fun submitForm(test: () -> Unit) {
        submitButton.performClick()
        test()
        submitButton.performClick()
    }

    @Before
    fun setUp() {
        Utils.signIn(rule)
        rule.onNodeWithTag("nav_to_profile").assertExists().performClick()
        rule.onNodeWithTag("change_password").assertExists().performClick()
    }

    @Test
    fun `1 Wrong password format show error`() {
        newPassword.performTextInput("abc123456")
        submitForm {
            rule.onNodeWithText(errors?.errorMeg3_2.toString()).assertExists()
        }
    }

    @Test
    fun `2 Password and CheckPassword not the same`() {
        newPassword.performTextInput("Abc123456")
        checkNewPassword.performTextInput("Abc1234567")
        submitForm {
            rule.onNodeWithText(errors?.errorMeg3_1!!).assertExists()
        }
    }

    @Test
    fun `3 Nav to profile after submit`() {
        newPassword.performTextInput("Abc654321")
        checkNewPassword.performTextInput("Abc654321")
        submitButton.performClick()
        rule.onNodeWithTag("profile_screen").assertExists()
    }

    @Test
    fun `4 SignOut enter old password will failed signIn`() {
        signOut.performClick()
    }

//    @Ignore
//    fun `5 SignOut enter new password will successfully signIn`() {
//
//    }
}