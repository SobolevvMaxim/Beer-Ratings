package com.sobolev.beerratings.data.local.di

import android.content.Context
import androidx.room.Room
import com.sobolev.beerratings.data.local.AppDatabase
import com.sobolev.beerratings.data.local.BookmarksDao
import com.sobolev.beerratings.data.local.RegionStore
import com.sobolev.beerratings.data.local.RegionStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

const val APP_DATABASE = "APP_DATABASE"

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class StoreModule {

    @Binds
    abstract fun bindPrefsStore(prefStore: RegionStoreImpl): RegionStore
}