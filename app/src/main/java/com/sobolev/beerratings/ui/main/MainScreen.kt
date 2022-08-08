package com.sobolev.beerratings.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sobolev.beerratings.ui.theme.DarkGray
import com.sobolev.beerratings.ui.theme.MainTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var sortState by remember { mutableStateOf(SortItemsBy.DATE) }

    Scaffold(
        bottomBar = {
            MainScreenBottomBar(sortItemsBy = sortState, onTabSelected = {
                sortState = it
            })
        },
        topBar = { MainScreenTopBar(state = textState) },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Divider(
                color = DarkGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 22.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            RatingsList(
                navController = navController,
                sortItemsBy = sortState,
                searchState = textState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MainScreenTopBar(state: MutableState<TextFieldValue>) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .height(163.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 22.dp, top = 25.dp, bottom = 16.dp),
        elevation = 0.dp
    ) {
        Column {
            Text(
                text = "Search",
                style = MaterialTheme.typography.labelSmall.copy(color = Color.DarkGray)
            )
            Spacer(modifier = Modifier.height(6.dp))
            SearchView(state = state)
            Spacer(modifier = Modifier.height(12.dp))
            FilterButtons()
        }
    }
}

@Composable
fun MainScreenBottomBar(sortItemsBy: SortItemsBy, onTabSelected: (tab: SortItemsBy) -> Unit) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        SortTab(sortItemsBy = sortItemsBy, onTabSelected = onTabSelected)
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, DarkGray), RoundedCornerShape(10.dp))
            .height(50.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
        trailingIcon = {
            when (state.value) {
                TextFieldValue("") -> Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(20.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                else -> IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
//            leadingIconColor = Color.Black,
//            trailingIconColor = Color.Black,
            backgroundColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

//        label = { Text(text = "Поиск по названию") }
    )
}

@Composable
private fun FilterButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(0.dp)) {
        FilterButton(
            text = "Sort By: New List", onClick = {

            },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        FilterButton(
            text = "Filters", onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun FilterButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.height(35.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        contentPadding = PaddingValues(bottom = 3.dp)
    ) {
        Row {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(color = DarkGray),
            )
            Icon(
                Icons.Default.ExpandMore,
                "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.DEFAULT,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewTopBar() {
    MainTheme() {
        val t = remember { mutableStateOf(TextFieldValue("")) }
        Scaffold(
            topBar = {
                MainScreenTopBar(state = t)
            },
            backgroundColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}