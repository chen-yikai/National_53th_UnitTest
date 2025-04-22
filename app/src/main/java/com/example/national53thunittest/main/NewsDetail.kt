package com.example.national53thunittest.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.national53thunittest.getNews

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsDetail(id: Int) {
    val nav = LocalMainNavController.current
    val context = LocalContext.current
    val news = getNews(context).find { it.id == id }


    Column(modifier = Modifier.statusBarsPadding()) {
        Row {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        }
        LazyColumn(contentPadding = PaddingValues(horizontal = 25.dp)) {
            stickyHeader {
                Column(modifier = Modifier.background(Color.White)) {
                    Column {
                        Spacer(Modifier.height(10.dp))
                        news?.let {
                            Text(
                                "${it.id}. ${it.title}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(it.organizer)
                                Text(it.publishDate)
                            }
                            Text("觀看次數: ${it.views}")
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }
            item {
                Text(news?.content.toString())
            }
        }
    }
}