package com.sobolev.beerratings.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobolev.beerratings.data.local.RegionStore
import com.sobolev.beerratings.domain.model.Bookmark
import com.sobolev.beerratings.domain.model.Rating
import com.sobolev.beerratings.domain.model.Region
import com.sobolev.beerratings.domain.repository.BookmarksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val regionStore: RegionStore,
    private val bookmarksRepository: BookmarksRepository
    ): ViewModel() {

    init {
        getCurrentBookmarks()
    }

    val chosenRegion = regionStore.getRegion()

    val bookmarks = mutableStateListOf<Bookmark>()

    val rating = mutableStateListOf<Rating>()

    fun postRatings(ratings: List<Rating>) {
        rating.clear()
        rating.addAll(ratings)
        Log.d("BEER", "new value: ${rating.toList()}")
    }

    fun changeRegion(chosenRegion: Region) {
        viewModelScope.launch {
            regionStore.changeRegion(chosenRegion.toString())
        }
    }

    fun addRatingToBookmarks(rating: Rating) {
        viewModelScope.launch {
            bookmarksRepository.addBookmark(Bookmark(rating.title))
            bookmarks.add(Bookmark(rating.title))
        }
    }

    fun removeRatingFromBookmarks(rating: Rating) {
        viewModelScope.launch {
            val list = bookmarksRepository.removeBookmark(Bookmark(rating.title))
            bookmarks.clear()
            bookmarks.addAll(list)
        }
    }

    fun getCurrentBookmarks() {
        viewModelScope.launch {
            val list = bookmarksRepository.getAllBookmarks()
            bookmarks.clear()
            bookmarks.addAll(list)
        }
    }
}