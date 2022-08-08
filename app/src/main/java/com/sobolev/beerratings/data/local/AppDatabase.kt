package com.sobolev.beerratings.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sobolev.beerratings.data.local.entities.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao
}