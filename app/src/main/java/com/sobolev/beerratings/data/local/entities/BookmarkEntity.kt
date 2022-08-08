package com.sobolev.beerratings.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sobolev.beerratings.data.local.BOOKMARKS_TABLE

@Entity(tableName = BOOKMARKS_TABLE)
data class BookmarkEntity(
    @PrimaryKey val title: String
)
