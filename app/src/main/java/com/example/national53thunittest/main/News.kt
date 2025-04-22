package com.example.national53thunittest.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.example.national53thunittest.AuthField
import com.example.national53thunittest.getNews

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen() {
    val nav = LocalMainNavController.current
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var sortBy by remember { mutableIntStateOf(0) }
    val news = getNews(context)

    LaunchedEffect(sortBy) {
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("最新消息") },
            navigationIcon = {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "")
                }
            }
        )
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = innerPadding.calculateTopPadding()))
                .padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                var searchInput by remember { mutableStateOf("") }
                AuthField(
                    searchInput,
                    { searchInput = it },
                    "搜尋",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(Modifier.width(10.dp))
                FilledTonalButton(onClick = {
                    searchQuery = searchInput
                }) {
                    Icon(Icons.Default.Search, contentDescription = "")
                }
            }
            Spacer(Modifier.height(10.dp))
            val items = listOf<String>("編號", "發布日期", "瀏覽數")
            LazyColumn(Modifier.clip(RoundedCornerShape(20.dp))) {
                stickyHeader {
                    Spacer(Modifier.height(11.dp))
                    SingleChoiceSegmentedButtonRow(modifier = androidx.compose.ui.Modifier.Companion.fillMaxWidth()) {
                        items.fastForEachIndexed { index, item ->
                            SegmentedButton(
                                selected = index == sortBy,
                                onClick = {
                                    sortBy = index
                                },
                                shape = SegmentedButtonDefaults.itemShape(index, items.size)
                            ) {
                                Text(item)
                            }
                        }
                    }
                }
                items(news) {
                    if (it.title.contains(searchQuery) || searchQuery.isEmpty())
                        Card(modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)) {
                            Column(
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 15.dp
                                )
                            ) {
                                Text(
                                    "${it.id}. ${it.title}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(it.organizer)
                                Spacer(Modifier.height(5.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(it.publishDate)
                                    Text("觀看次數: ${it.views}")
                                }
                            }
                        }
                }
                item {
                    Spacer(Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
                }
            }
        }
    }
}