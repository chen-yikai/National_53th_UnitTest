package com.example.national53thunittest

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.core.content.edit
import com.example.national53thunittest.main.AuthScreens
import com.example.national53thunittest.main.LocalMainNavController
import com.example.national53thunittest.main.MainScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.WatchEvent

@Composable
fun AuthField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    isError: Boolean = false,
    isPassword: Boolean = false,
    tag: String = "auth_field",
    trailingIcon: @Composable () -> Unit = {}, modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label) },
        modifier = Modifier
            .padding(vertical = 5.dp)
            .testTag(tag)
            .then(modifier),
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        trailingIcon = trailingIcon
    )
}

@Composable
fun SignIn() {
    val nav = LocalMainNavController.current
    val context = LocalContext.current
    val db = LocalRoomDataBase.current
    val scope = rememberCoroutineScope()
    val usersModel = UsersModel(db)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)

    var showAlert by remember { mutableStateOf(false) }
    val alertMsg = remember { mutableStateListOf<String>() }

    if (showAlert) {
        AuthAlertDialog(
            msg = alertMsg,
            dismiss = { showAlert = false },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("SignInScreen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Sh(40.dp)
        AuthField(
            value = email,
            onValueChange = { email = it },
            label = "電子郵件",
            tag = "emailInput"
        )
        AuthField(
            value = password,
            onValueChange = { password = it },
            label = "密碼",
            isPassword = true, tag = "passwordInput"
        )
        Sh(40.dp)
        Button(modifier = Modifier.testTag("login"), onClick = {
            val errorMsg = getError(context, "LoginPage")
            alertMsg.clear()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                scope.launch {
                    val authed = usersModel.signIn(email, password)
                    if (authed) {
                        withContext(Dispatchers.Main) {
                            nav.navigate(MainScreens.Home.name)
                        }
                        sharedPreferences.edit() {
                            putString("email", email)
                        }
                    } else {
                        alertMsg.add(errorMsg?.errorMeg1_2.toString())
                        showAlert = true
                    }
                }
            } else {
                alertMsg.add(errorMsg?.errorMeg1_1.toString())
                showAlert = true
            }
        }) {
            Text("登入")
        }
        Sh()
        FilledTonalButton(
            onClick = { nav.navigate(AuthScreens.SignUp.name) },
            modifier = Modifier.testTag("nav_signUp")
        ) {
            Text("註冊")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthAlertDialog(msg: List<String>, dismiss: () -> Unit) {
    AlertDialog(
        title = { Text("錯誤訊息") },
        text = {
            LazyColumn {
                items(msg) {
                    Text(it)
                }
            }
        },
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(onClick = dismiss) { Text("確定") }
        },
    )
}

@Composable
fun SignUp() {
    val nav = LocalMainNavController.current
    val database = LocalRoomDataBase.current
    val usersModel = UsersModel(database)
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }

    var showAlert by remember { mutableStateOf(false) }
    val alertMsg = remember { mutableStateListOf<String>() }

    var hidePassword by remember { mutableStateOf(true) }
    var hidePasswordCheck by remember { mutableStateOf(true) }

    if (showAlert) {
        AuthAlertDialog(
            msg = alertMsg,
            dismiss = { showAlert = false },
        )
    }

    Box(
        Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
        Column(
            Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Register", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Sh(40.dp)
            AuthField(name, { name = it }, label = "姓名", isError = false)
            AuthField(email, { email = it }, label = "Email", isError = false)
            AuthField(
                password,
                { password = it },
                label = "密碼",
                isPassword = hidePassword,
                trailingIcon = {
                    IconButton(onClick = { hidePassword = !hidePassword }) {
                        Icon(
                            painter = painterResource(if (hidePassword) R.drawable.visibility else R.drawable.visibility_off),
                            modifier = Modifier.testTag("toggle_visibility"),
                            contentDescription = ""
                        )
                    }
                })
            AuthField(
                passwordCheck,
                { passwordCheck = it },
                label = "再次輸入密碼",
                isError = false,
                isPassword = hidePasswordCheck,
                trailingIcon = {
                    IconButton(onClick = { hidePasswordCheck = !hidePasswordCheck }) {
                        Icon(
                            painter = painterResource(if (hidePasswordCheck) R.drawable.visibility else R.drawable.visibility_off),
                            modifier = Modifier.testTag("toggle_visibility_a"),
                            contentDescription = ""
                        )
                    }
                }
            )
            Sh(40.dp)
            fun checkFormat() {
                val passWord = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,15}$"
                alertMsg.clear()
                val errorMsg = getError(context, "RegistrationPage")
                var error = false
                if (name.isEmpty() || name.length > 10) {
                    alertMsg.add(errorMsg?.errorMeg2_2.toString())
                    error = true
                }
                if (email.isEmpty() || email.length > 30 || !Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches()
                ) {
                    alertMsg.add(errorMsg?.errorMeg2_3.toString())
                    error = true
                }
                if (password.isEmpty() || !password.matches(passWord.toRegex()) || !passwordCheck.matches(
                        passWord.toRegex()
                    )
                ) {
                    alertMsg.add(errorMsg?.errorMeg2_4.toString())
                    error = true
                }
                if (password != passwordCheck) {
                    alertMsg.add(errorMsg?.errorMeg2_1.toString())
                    error = true
                }

                if (!error) {
                    usersModel.signUp(name, email, password)
                    nav.navigate(AuthScreens.SignIn.name)
                } else {
                    showAlert = true
                }
            }
            Button(onClick = {
                checkFormat()
            }) {
                Text("註冊")
            }
        }
    }
}