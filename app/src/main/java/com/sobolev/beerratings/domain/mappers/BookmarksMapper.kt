package com.sobolev.beerratings.domain.mappers

import com.sobolev.beerratings.data.local.entities.BookmarkEntity
import com.sobolev.beerratings.domain.model.Bookmark

object BookmarksMapper {
    fun Bookmark.toEntity() = BookmarkEntity(this.title)

    fun BookmarkEntity.toBookmark() = Bookmark(this.title)
}