package com.example.national53thunittest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController =
                TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            Router(navController)
        }
    }

    @Test
    fun signUp() {
        Thread.sleep(1000)
        rule.onNodeWithTag("hello").performTextInput("hello world testing")
        rule.onNodeWithTag("nav_signUp").performClick()
        Thread.sleep(3000)
    }

}