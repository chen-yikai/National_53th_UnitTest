package com.example.national53thunittest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AuthField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    isError: Boolean = false,
    isPassword: Boolean = false,
    tag: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label) },
        modifier = Modifier
            .padding(vertical = 5.dp)
            .testTag(tag),
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError
    )
}

@Composable
fun SignIn(nav: NavController, textFieldColors: TextFieldColors) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Sh(40.dp)
        AuthField(
            value = email,
            onValueChange = { email = it },
            label = "電子郵件",
            tag = "hello"
        )
        AuthField(
            value = password,
            onValueChange = { password = it },
            label = "密碼",
            isPassword = true
        )
        Sh(40.dp)
        Button(onClick = {}) {
            Text("登入")
        }
        Sh()
        FilledTonalButton(
            onClick = { nav.navigate(Screens.SignUp.name) },
            modifier = Modifier.testTag("nav_signUp")
        ) {
            Text("註冊")
        }
    }
}

@Composable
fun SignUp(nav: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Sh(40.dp)
        AuthField(name, { name = it }, label = "姓名", isError = false)
        AuthField(email, { email = it }, label = "Email", isError = false)
        AuthField(password, { password = it }, label = "密碼", isError = false)
        AuthField(passwordCheck, { passwordCheck = it }, label = "再次輸入密碼", isError = false)
        Sh(40.dp)
        fun checkFormat() {
            val errorMsg = getError(context, "RegistrationPage")
            var error = false
            if (name.isEmpty() || name.length > 10) {
                toaster(
                    context,
                    errorMsg?.errorMeg2_2.toString()
                )
                error = true
            }
            if (email.isEmpty() || email.length > 30 || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                    email
                ).matches()
            ) {
                toaster(context, errorMsg?.errorMeg2_3.toString())
                error = true
            }
            if (password.isEmpty() || !password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,15}$".toRegex())) {
                toaster(
                    context,
                    errorMsg?.errorMeg2_4.toString()
                )
                error = true
            }
            if (password != passwordCheck) {
                toaster(context, errorMsg?.errorMeg2_1.toString())
                error = true
            }
            if (!error) {

            }
        }
        Button(onClick = {
            checkFormat()
        }) {
            Text("註冊")
        }
    }
}