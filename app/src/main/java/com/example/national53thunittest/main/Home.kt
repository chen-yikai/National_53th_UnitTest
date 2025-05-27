package com.example.national53thunittest.main

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.national53thunittest.getNews
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val drawerState = LocalDrawerState.current
    val nav = LocalMainNavController.current
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { scope.launch { drawerState.open() } },
                modifier = Modifier.testTag("open_drawer")
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = ""
                )
            }
            IconButton(
                onClick = { nav.navigate(MainScreens.Profile.name) },
                modifier = Modifier.testTag("nav_to_profile")
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = ""
                )
            }
        }
        val data = getNews(context)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)
        ) {
            item {
                Text("News", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))
            }
            var count = 1;
            items(data) {
                if (count <= 5) Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .testTag("home_news_item")
                ) {
                    val title = "${it.id}. ${it.title}"
                    Text(
                        if (title.length > 30) title.take(30) + "..." else title,
                        maxLines = 1,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable {
                                nav.navigate("${MainScreens.NewsDetail.name}/${it.id}")
                            }
                    )
                }
                count++
            }
        }
    }
}