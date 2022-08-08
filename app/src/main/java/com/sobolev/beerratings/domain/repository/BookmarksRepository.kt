package com.sobolev.beerratings.domain.repository

import com.sobolev.beerratings.domain.model.Bookmark

interface BookmarksRepository {

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun removeBookmark(bookmark: Bookmark): List<Bookmark>

    suspend fun getAllBookmarks(): List<Bookmark>
}