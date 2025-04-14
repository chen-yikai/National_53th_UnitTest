package com.example.national53thunittest

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Composable
fun Sh(height: Dp = 20.dp) {
    Spacer(Modifier.height(height))
}

fun toaster(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

data class Error(
    @SerializedName("Page") val page: String,
    val errorMeg1_1: String? = null,
    val errorMeg1_2: String? = null,
    val errorMeg2_1: String? = null,
    val errorMeg2_2: String? = null,
    val errorMeg2_3: String? = null,
    val errorMeg2_4: String? = null,
    val errorMeg3_1: String? = null,
    val errorMeg3_2: String? = null
)

fun getError(context: Context, page: String): Error {
    context.assets.open("錯誤訊息庫_dev.json").bufferedReader().use {
        return Gson().fromJson(it, Array<Error>::class.java).first { it.page == page }
    }
}