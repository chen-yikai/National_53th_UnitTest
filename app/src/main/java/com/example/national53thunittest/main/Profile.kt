package com.example.national53thunittest.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.national53thunittest.LocalRoomDataBase
import com.example.national53thunittest.Users
import com.example.national53thunittest.UsersModel
import androidx.core.content.edit
import com.example.national53thunittest.AuthField
import com.example.national53thunittest.getError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val nav = LocalMainNavController.current
    val context = LocalContext.current
    val db = LocalRoomDataBase.current
    val authNav = LocalMainNavController.current
    val usersModel = UsersModel(db)
    val email = context.getSharedPreferences("app", Context.MODE_PRIVATE).getString("email", "")
    var info by remember { mutableStateOf<Users?>(null) }
    var showEditPassword by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        info = usersModel.getInfo(email!!)
    }

    if (showEditPassword) EditPassword { showEditPassword = false }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("個人資訊") },
            navigationIcon = {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "")
                }
            }
        )
    }) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp).testTag("profile_screen"), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                info?.let {
                    InfoList("姓名", it.name, 30.sp)
                    Spacer(Modifier.height(20.dp))
                    InfoList("Email", it.email)
                }
                Spacer(Modifier.height(20.dp))
                FilledTonalButton(
                    onClick = { showEditPassword = true },
                    modifier = Modifier.testTag("change_password")
                ) { Text("修改密碼") }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    authNav.navigate(AuthScreens.SignIn.name)
                    context.getSharedPreferences("app", Context.MODE_PRIVATE).edit() { clear() }
                }, modifier = Modifier.testTag("sign_out")) {
                    Text("登出")
                }
            }
        }
    }
}

@Composable
fun EditPassword(dismiss: () -> Unit) {
    val context = LocalContext.current
    val db = LocalRoomDataBase.current
    val usersModel = UsersModel(db)
    val email = context.getSharedPreferences("app", Context.MODE_PRIVATE).getString("email", "")
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var errorMsg = remember { mutableStateListOf<String>() }

    Dialog(onDismissRequest = dismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .testTag("change_password_dialog"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("修改密碼")
            Spacer(Modifier.height(20.dp))
            if (errorMsg.isNotEmpty()) {
                LazyColumn {
                    items(errorMsg) {
                        Text(
                            it,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }
            } else {
                AuthField(password, { password = it }, label = "新密碼", tag = "new_password")
                AuthField(
                    passwordCheck,
                    { passwordCheck = it },
                    label = "確認新密碼",
                    tag = "check_new_password"
                )
            }
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = dismiss) { Text("取消") }
                TextButton(modifier = Modifier.testTag("submit_button"), onClick = {
                    if (errorMsg.isNotEmpty()) {
                        errorMsg.clear()
                    } else {
                        if (password.isEmpty() || !password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,15}$".toRegex())) {
                            errorMsg += getError(
                                context,
                                "ChangePasswordPage"
                            )?.errorMeg3_2.toString()
                        }
                        if (password != passwordCheck) {
                            errorMsg += getError(
                                context,
                                "ChangePasswordPage"
                            )?.errorMeg3_1.toString()
                        }
                        if (errorMsg.isEmpty()) {
                            usersModel.updatePassword(password, email!!)
                            dismiss()
                        }
                    }
                }) { Text("確定") }
            }
        }
    }
}

@Composable
fun InfoList(label: String, content: String, size: TextUnit = 20.sp) {
    Column {
        Text(label, color = Color.Gray)
        Text(
            content, fontWeight = FontWeight.Bold, fontSize = size, modifier = Modifier.testTag(
                label
            )
        )
    }
}