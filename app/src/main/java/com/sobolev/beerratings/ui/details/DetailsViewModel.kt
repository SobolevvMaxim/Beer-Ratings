package com.sobolev.beerratings.ui.details

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObjects
import com.sobolev.beerratings.domain.model.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(

) : ViewModel() {

    val comments = mutableStateListOf<Comment>()

    fun addComment(comment: Comment, commentQuery: CollectionReference) {
        comments.add(comment)
        val commentToAdd = hashMapOf(
            "comment" to comment.comment,
            "username" to comment.username,
            "user_rating" to comment.user_rating
        )
        commentQuery.document(comment.username).set(
            commentToAdd
        )
    }

    fun getAllComments(commentQuery: CollectionReference) {
        commentQuery.get().addOnSuccessListener {
            comments.clear()
            comments.addAll(it.toObjects())
        }
    }
}