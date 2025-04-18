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
    @SerializedName("ErrorMeg1-1") val errorMeg1_1: String? = null,
    @SerializedName("ErrorMeg1-2") val errorMeg1_2: String? = null,
    @SerializedName("ErrorMeg2-1") val errorMeg2_1: String? = null,
    @SerializedName("ErrorMeg2-2") val errorMeg2_2: String? = null,
    @SerializedName("ErrorMeg2-3") val errorMeg2_3: String? = null,
    @SerializedName("ErrorMeg2-4") val errorMeg2_4: String? = null,
    @SerializedName("ErrorMeg3-1") val errorMeg3_1: String? = null,
    @SerializedName("ErrorMeg3-2") val errorMeg3_2: String? = null
)

fun getError(context: Context, page: String): Error? {
    context.assets.open("2.錯誤訊息庫_dev.json").bufferedReader().use {
        val data = Gson().fromJson(it, Array<Error>::class.java).toList()
        return data.find { item -> item.page == page }
    }
}