package com.example.marthaspantrymore.ui.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.marthaspantrymore.ui.domain.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsStore() {
    val tabs = listOf(
        ItemsTabStore.sellTab,
        ItemsTabStore.buyTab
    )
    val pagerState = rememberPagerState { 2 }
    Column {
        Tabs(tabs, pagerState)
        TabsContent(tabs, pagerState)
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(tabs: List<ItemsTabStore>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(tabs: List<ItemsTabStore>, pagerState: PagerState) {
    var selectedTab = pagerState.currentPage
    var scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, items ->
            Tab(
                selected = selectedTab == index,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                text = { Text(text = items.title) },
                icon = {
                    Icon(
                        if (index == selectedTab)
                            items.iconSelected
                        else
                            items.iconUnselected, contentDescription = items.title
                    )
                }
            )
        }
    }
}
