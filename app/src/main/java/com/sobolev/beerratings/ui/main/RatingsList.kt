package com.sobolev.beerratings.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.RatingStar
import com.gowtham.ratingbar.starRating
import com.sobolev.beerratings.domain.model.Bookmark
import com.sobolev.beerratings.domain.model.Rating
import com.sobolev.beerratings.ui.theme.ActiveStar
import com.sobolev.beerratings.ui.theme.MainTheme
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))

val groupDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

@Composable
fun RatingsList(
    navController: NavController,
    searchState: MutableState<TextFieldValue>,
    sortItemsBy: SortItemsBy,
    viewModel: MainViewModel,
) {
    val list = getFilteredListBySearchState(viewModel.rating, searchState)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
//            .fillMaxHeight(0.93f) // TODO: Fit list with bottom bar
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (sortItemsBy) {
            SortItemsBy.TITLE ->
                items(list.sortedBy { it.title }) {
                    DefaultListItem(
                        navController = navController,
                        rating = it,
                        onBookmarkPressed = { rating: Rating, added: Boolean ->
                            when (added) {
                                true -> viewModel.removeRatingFromBookmarks(rating)
                                false -> viewModel.addRatingToBookmarks(rating)
                            }
                        },
                        bookmarks = viewModel.bookmarks
                    )
                }
            else -> list
                .sortedByDescending {
                    when (sortItemsBy) {
                        SortItemsBy.RATING -> {
                            it.rating
                        }
                        else -> {
                            (dateFormat.parse(it.date) ?: Date(1)).time.toDouble()
                        }
                    }
                }
                .groupBy {
                    when (sortItemsBy) {
                        SortItemsBy.RATING -> it.rating
                        else -> it.date
                    }
                }
                .forEach { (t, u) ->
                    item {
                        when (sortItemsBy) {
                            SortItemsBy.DATE -> GroupingItem(text = formatGroupItemDate(t.toString()))
                            else -> GroupingItem(text = t.toString())
                        }
                    }
                    items(u) {
                        DefaultListItem(
                            navController = navController,
                            rating = it,
                            onBookmarkPressed = { rating: Rating, added: Boolean ->
                                when (added) {
                                    true -> viewModel.removeRatingFromBookmarks(rating)
                                    false -> viewModel.addRatingToBookmarks(rating)
                                }
                            },
                            bookmarks = viewModel.bookmarks
                        )
                    }
                }

        }
    }
}

fun getFilteredListBySearchState(
    currentList: SnapshotStateList<Rating>,
    searchState: MutableState<TextFieldValue>,
): SnapshotStateList<Rating> {
    val searchedText = searchState.value.text
    return currentList.filter {
        it.title.lowercase(Locale.getDefault()).contains(
            searchedText.lowercase(Locale.getDefault())
        )
    }.toMutableStateList()
}

@Composable
fun DefaultListItem(
    navController: NavController,
    onBookmarkPressed: (Rating, added: Boolean) -> Unit,
    bookmarks: SnapshotStateList<Bookmark>,
    rating: Rating
) {
    RatingListItem(
        rating = rating,
        onItemClick = { clickedRating ->
            navController.navigate("details/${clickedRating.title}") {
                Log.d("PRESSED", "Pressed on ${clickedRating.title}")
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo("main") {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
        onBookmarkPressed = onBookmarkPressed,
        bookmarks = bookmarks
    )
}

@Composable
fun GroupingItem( // TODO: Better Grouping Rating Item
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.W300,
            color = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

fun formatGroupItemDate(date: String): String =
    groupDateFormat.format(dateFormat.parse(date) ?: Date(1))

@Composable
fun RatingListItem(
    rating: Rating,
    onItemClick: (Rating) -> Unit,
    bookmarks: SnapshotStateList<Bookmark>,
    onBookmarkPressed: (Rating, added: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Advanced rating (line?)
    // TODO: Label "New Rating" if 2 days or newer
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onItemClick(rating) }
    ) {
        SubcomposeAsyncImage(
            model = rating.imageUrl,
            contentDescription = null,
            loading = {
                ImageLoading()
            },
            modifier = Modifier
                .height(110.dp)
                .width(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,

            )
        Column(
            modifier = modifier.padding(start = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp) // TODO: Better arrangement
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = rating.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1
                )
                val added = bookmarks.contains(Bookmark(rating.title))
                IconButton(onClick = { onBookmarkPressed(rating, added) }) {
                    when (added) {
                        true -> Icon(
                            Icons.Default.Bookmark,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        false -> Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }

            Text(
                text = "Public rating: 3.24", style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp
                )
            )
            ComposeStars(
                value = rating.rating.toFloat(),
                config = RatingBarConfig()
                    .activeColor(ActiveStar)
                    .style(RatingBarStyle.HighLighted)
                    .inactiveColor(Color.Transparent)
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun ImageLoading() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        // `infiniteRepeatable` repeats the specified duration-based `AnimationSpec` infinitely.
        animationSpec = infiniteRepeatable(
            // The `keyframes` animates the value by specifying multiple timestamps.
            animation = keyframes {
                // One iteration is 1000 milliseconds.
                durationMillis = 1000
                // 0.7f at the middle of an iteration.
                0.7f at 500
            },
            // When the value finishes animating from 0f to 1f, it repeats by reversing the
            // animation direction.
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .height(80.dp)
            .width(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = alpha))
    )
}

@Composable
fun ComposeStars(
    value: Float,
    config: RatingBarConfig
) {

    val ratingPerStar = 1f
    var remainingRating = value

    Row(modifier = Modifier
        .semantics { starRating = value }) {
        for (i in 1..config.numStars) {
            val starRating = when {
                remainingRating == 0f -> {
                    0f
                }
                remainingRating >= ratingPerStar -> {
                    remainingRating -= ratingPerStar
                    1f
                }
                else -> {
                    val fraction = remainingRating / ratingPerStar
                    remainingRating = 0f
                    fraction
                }
            }
            if (config.hideInactiveStars && starRating == 0.0f)
                break
            RatingStar(
                fraction = starRating,
                config = config,
                modifier = Modifier
                    .padding(
                        start = if (i > 1) config.padding else 0.dp,
                        end = if (i < config.numStars) config.padding else 0.dp
                    )
                    .size(size = config.size)
                    .testTag("RatingStar")
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    MainTheme {
        val rating = remember {
            mutableStateOf(
                Rating(
                    "Beer title",
                    "as;dflja;lsdkfj sad;lfj;n va;sldjf;lkasvnm",
                    "",
                    4.5,
                    "30.10.2022",
                    3.5
                )
            )
        }
        RatingListItem(
            rating = rating.value,
            onItemClick = {},
            onBookmarkPressed = { _, _ -> rating.value.addedToBookmarks = !rating.value.addedToBookmarks },
            bookmarks = mutableStateListOf()
        )
    }
}