package com.example.national53thunittest.main

import android.graphics.drawable.Icon
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.example.national53thunittest.AuthField
import com.example.national53thunittest.News
import com.example.national53thunittest.R
import com.example.national53thunittest.getNews

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen() {
    val nav = LocalMainNavController.current
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var sortBy by remember { mutableIntStateOf(0) }
    var news = remember { mutableStateListOf<News>() }
    var isAsc by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        news.addAll(getNews(context))
    }

    LaunchedEffect(sortBy, isAsc) {
        val sortedNews = when (sortBy) {
            0 -> if (!isAsc) news.sortedByDescending { it.id } else news.sortedBy { it.id }
            1 -> if (!isAsc) news.sortedByDescending { it.publishDate } else news.sortedBy { it.publishDate }
            2 -> if (!isAsc) news.sortedByDescending { it.views } else news.sortedBy { it.views }
            else -> news.toList()
        }
        news.clear()
        news.addAll(sortedNews)
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
                .testTag("news_screen")
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
                        .weight(1f), tag = "search_news"
                )
                Spacer(Modifier.width(10.dp))
                FilledTonalButton(onClick = {
                    searchQuery = searchInput
                }, modifier = Modifier.testTag("search_button")) {
                    Icon(Icons.Default.Search, contentDescription = "")
                }
            }
            Spacer(Modifier.height(10.dp))
            val items = listOf<String>("編號", "發布日期", "瀏覽數")
            LazyColumn(Modifier.clip(RoundedCornerShape(20.dp))) {
                stickyHeader {
                    Spacer(Modifier.height(11.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        items.fastForEachIndexed { index, item ->
                            SegmentedButton(
                                selected = index == sortBy,
                                onClick = {
                                    if (index == sortBy) {
                                        isAsc = !isAsc
                                    } else {
                                        sortBy = index
                                        isAsc = false
                                    }
                                },
                                icon = {
                                    if (index == sortBy)
                                        if (isAsc)
                                            Icon(
                                                painter = painterResource(R.drawable.outline_arrow_upward_24),
                                                contentDescription = null,
                                                modifier = Modifier.testTag("asc")
                                            )
                                        else
                                            Icon(
                                                painter = painterResource(R.drawable.outline_arrow_downward_24),
                                                contentDescription = null,
                                                modifier = Modifier.testTag("des")
                                            )
                                },
                                shape = SegmentedButtonDefaults.itemShape(index, items.size),
                                modifier = Modifier.testTag(item)
                            ) {
                                Row {
                                    Text(item)
                                }
                            }
                        }
                    }
                }
                items(news) {
                    if (it.title.contains(searchQuery) || searchQuery.isEmpty())
                        Card(
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 5.dp)
                                .testTag("news_card")
                                .testTag("news_card_${it.id}"),
                            onClick = {
                                nav.navigate("${MainScreens.NewsDetail}/${it.id}")
                            }) {
                            Column(
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 15.dp
                                )
                            ) {
                                Text(
                                    "${it.id}. ${it.title}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .testTag("title")
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(it.organizer, modifier = Modifier.testTag("organizer"))
                                Spacer(Modifier.height(5.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        it.publishDate,
                                        modifier = Modifier.testTag("publish_date")
                                    )
                                    Text(
                                        "觀看次數: ${it.views}",
                                        modifier = Modifier.testTag("view_count")
                                    )
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