package com.sobolev.beerratings.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.CollectionReference
import com.sobolev.beerratings.domain.model.Comment
import com.sobolev.beerratings.domain.model.Rating
import com.sobolev.beerratings.ui.main.RatingListItem

@SuppressLint("UnrememberedMutableState")
@Composable
fun DetailScreen(
    rating: Rating,
    viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RatingListItem(rating = rating, onItemClick = { }, onBookmarkPressed = { _, _ ->}, bookmarks = mutableStateListOf())
            
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                items(viewModel.comments) { comment ->
//                    CommentItem(comment = comment)
//                }
//            }
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
) {
    Column {
        Text(text = comment.username)
        Text(text = comment.comment)
        Text(text = comment.user_rating.toString())
    }
}

@Composable
fun AddComment(viewModel: DetailsViewModel, commentsQuery: CollectionReference) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var username by remember { mutableStateOf("") }
        var comment by remember { mutableStateOf("") }
        TextField(value = username, onValueChange = { username = it})
        TextField(value = comment, onValueChange = { comment = it})
        Button(
            onClick = {
                viewModel.addComment(comment = Comment(
                    username, comment, 1.0
                ), commentsQuery)
            }
        ) {
            Text(text = "Add comment")
        }
    }
}