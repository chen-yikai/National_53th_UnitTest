package com.example.national53thunittest

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    SignUp::class,
    SignIn::class,
    Home::class
)
class UnitTestEntry