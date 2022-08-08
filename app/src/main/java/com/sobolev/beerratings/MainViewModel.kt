package com.sobolev.beerratings

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.sobolev.beerratings.model.Rating
import com.sobolev.beerratings.model.Ratings

class MainViewModel: ViewModel() {

    val rating = mutableStateListOf<Rating>()

    fun postRatings(ratings: Ratings) {
        rating.clear()
        rating.addAll(ratings.ratings)
        Log.d("BEER", "new value: ${rating.toList()}")
    }
}