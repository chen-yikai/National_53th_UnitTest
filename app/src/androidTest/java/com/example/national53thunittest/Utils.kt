package com.example.national53thunittest

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule

object Utils {
    fun signIn(rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        val emailInput = rule.onNodeWithTag("emailInput")
        val passwordInput = rule.onNodeWithTag("passwordInput")
        val loginButton = rule.onNodeWithTag("login")

        emailInput.performTextInput("example@example.com")
        passwordInput.performTextInput("Abc123456")
        loginButton.performClick()
    }
}