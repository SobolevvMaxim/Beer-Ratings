package com.sobolev.beerratings.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobolev.beerratings.data.local.entities.BookmarkEntity
import com.sobolev.beerratings.domain.model.Bookmark

const val BOOKMARKS_TABLE = "BOOKMARKS_TABLE"

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmarkEntity: BookmarkEntity)

    @Query("SELECT * FROM $BOOKMARKS_TABLE")
    fun getAllBookmarks(): List<BookmarkEntity>

    @Query("DELETE FROM $BOOKMARKS_TABLE WHERE title = :bookmarkTitle")
    fun deleteBookmark(bookmarkTitle: String)
}