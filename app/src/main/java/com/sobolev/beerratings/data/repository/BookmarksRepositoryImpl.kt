package com.sobolev.beerratings.data.repository

import com.sobolev.beerratings.data.local.BookmarksDao
import com.sobolev.beerratings.domain.mappers.BookmarksMapper.toBookmark
import com.sobolev.beerratings.domain.mappers.BookmarksMapper.toEntity
import com.sobolev.beerratings.domain.model.Bookmark
import com.sobolev.beerratings.domain.repository.BookmarksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(
    private val bookmarksDao: BookmarksDao,
) : BookmarksRepository {
    private var currentBookmarks: Set<Bookmark>? = null

    override suspend fun addBookmark(bookmark: Bookmark) {
        withContext(Dispatchers.IO) {
            bookmarksDao.addBookmark(bookmark.toEntity())
            insertInMemory(bookmark)
        }
    }

    override suspend fun removeBookmark(bookmark: Bookmark): List<Bookmark> {
        withContext(Dispatchers.IO) {
            bookmarksDao.deleteBookmark(bookmark.title)
            deleteFromMemory(bookmark)
        }

        return currentBookmarks?.toList() ?: emptyList()
    }

    private fun deleteFromMemory(bookmark: Bookmark) {
        currentBookmarks = currentBookmarks?.toMutableSet()?.let { bookmarks ->
            bookmarks.remove(bookmark)
            bookmarks
        }
    }

    private fun insertInMemory(bookmark: Bookmark) {
        currentBookmarks = currentBookmarks?.toMutableSet()?.apply {
            add(bookmark)
        } ?: setOf(bookmark)
    }


    override suspend fun getAllBookmarks(): List<Bookmark> {
        return withContext(Dispatchers.IO) {
            currentBookmarks?.toList() ?: bookmarksDao.getAllBookmarks().toList()
                .map { it.toBookmark() }
        }
    }
}