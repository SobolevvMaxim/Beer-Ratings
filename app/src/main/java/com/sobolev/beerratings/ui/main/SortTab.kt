package com.sobolev.beerratings.ui.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class SortItemsBy {
    DATE, RATING, TITLE
}

@Composable
fun SortTab(sortItemsBy: SortItemsBy, onTabSelected: (tab: SortItemsBy) -> Unit) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = sortItemsBy.ordinal,
            indicator = {
                SortTabIndicator(tabPositions = it, tabPage = sortItemsBy)
            },
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            SortTabItem(
                title = "По дате",
                onClick = { onTabSelected(SortItemsBy.DATE) }
            )
            SortTabItem(
                title = "По оценкам",
                onClick = { onTabSelected(SortItemsBy.RATING) }
            )
            SortTabItem(
                title = "По названию",
                onClick = { onTabSelected(SortItemsBy.TITLE) }
            )
        }
    }
}

@Composable
private fun SortTabItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp)
    }
}

@Composable
private fun SortTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: SortItemsBy
) {
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            when {
                SortItemsBy.DATE isTransitioningTo SortItemsBy.RATING || SortItemsBy.DATE isTransitioningTo SortItemsBy.TITLE || SortItemsBy.RATING isTransitioningTo SortItemsBy.TITLE -> {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
                else -> spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            when {
                SortItemsBy.DATE isTransitioningTo SortItemsBy.RATING || SortItemsBy.DATE isTransitioningTo SortItemsBy.TITLE || SortItemsBy.RATING isTransitioningTo SortItemsBy.TITLE -> {
                    spring(stiffness = Spring.StiffnessMedium)
                }
                else -> spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
//    val color by transition.animateColor(
//        label = "Border color"
//    ) { page ->
//        if (page == SortItemsBy.DATE) Purple80 else Green200
//    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(4.dp)
            )
    )
}