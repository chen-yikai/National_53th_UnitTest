package com.example.national53thunittest

import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUp {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val error: Error? = getError(context, "RegistrationPage", "test")

    fun submitForm(meanWhiled: () -> Unit) {
        rule.onNodeWithText("註冊").performClick()
        meanWhiled()
        rule.onNodeWithText("確定").assertExists().performClick()
        rule.onNodeWithText("Register").assertExists()
    }

    @Before
    fun `Nav to SignUp`() {
        rule.onNodeWithText("註冊").assertExists().performClick()
    }

    @Test
    fun `1 Empty Field WILL Get Error`() {
        rule.onNodeWithText("Register").assertExists()
        rule.onAllNodesWithTag("auth_field").fetchSemanticsNodes()
            .forEachIndexed { index, _ ->
                rule.onAllNodesWithTag("auth_field")[index].performTextClearance()
            }
        submitForm {
            error?.let {
                rule.onNodeWithText(it.errorMeg2_1.toString()).assertExists()
                rule.onNodeWithText(it.errorMeg2_2.toString()).assertExists()
                rule.onNodeWithText(it.errorMeg2_3.toString()).assertExists()
            }
        }
    }

    @Test
    fun `2 Name can not contain more than 10 characters`() {
        rule.onNodeWithText("姓名").assertExists().performTextInput("t".repeat(12))
        submitForm {
            error?.let {
                rule.onNodeWithText(it.errorMeg2_2.toString()).assertExists()
            }
        }
    }

    @Test
    fun `3 Email can not contain more than 30 characters`() {
        rule.onNodeWithText("Email").assertExists()
            .performTextInput("t".repeat(31) + "@example.com")
        submitForm {
            error?.let {
                rule.onNodeWithText(it.errorMeg2_3.toString()).assertExists()
            }
        }
    }

    @Test
    fun `4 Check email format`() {
        rule.onNodeWithText("Email").assertExists().performTextInput("example")
        submitForm {
            error?.let {
                rule.onNodeWithText(it.errorMeg2_3.toString()).assertExists()
            }
        }
    }

    @Test
    fun `5 Check password format`() {
        rule.onNodeWithText("密碼").assertExists().performTextInput("example")
        submitForm {
            error?.let {
                rule.onNodeWithText(it.errorMeg2_4.toString()).assertExists()
            }
        }
    }

    @Ignore("How to check whether the password dot is show")
    fun `6 Change the visibility of the password to show`() {
        val password = rule.onNodeWithText("密碼")
        val passwordCheck = rule.onNodeWithText("再次輸入密碼")

        password.assertExists().performTextInput("example")
        passwordCheck.assertExists().performTextInput("example")

        // when hidden
        password.assert(hasText("hello"))

        rule.onNodeWithTag("toggle_visibility").performClick()
    }
}


