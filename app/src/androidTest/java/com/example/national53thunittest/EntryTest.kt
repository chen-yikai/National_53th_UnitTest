package com.example.national53thunittest

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SignUpTest::class,
    SignInTest::class,
    HomeTest::class,
    ProfileTest::class,
    ChangePassword::class
)
class UnitTestEntry

fun signIn(rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    val emailInput = rule.onNodeWithTag("emailInput")
    val passwordInput = rule.onNodeWithTag("passwordInput")
    val loginButton = rule.onNodeWithTag("login")

    emailInput.performTextInput("example@example.com")
    passwordInput.performTextInput("Abc123456")
    loginButton.performClick()
}

fun signUp(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    runNavToSignUp: Boolean = true
) {
    if (runNavToSignUp) rule.onNodeWithText("註冊").assertExists().performClick()
    val name = rule.onNodeWithText("姓名")
    val email = rule.onNodeWithText("Email")
    val password = rule.onNodeWithText("密碼")
    val passwordCheck = rule.onNodeWithText("再次輸入密碼")

    name.performTextInput("user")
    email.performTextInput("example@example.com")
    password.performTextInput("Abc123456")
    passwordCheck.performTextInput("Abc123456")

    rule.onNodeWithText("註冊").performClick()
}
