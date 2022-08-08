package com.sobolev.beerratings.data.repository.di

import com.sobolev.beerratings.data.repository.BookmarksRepositoryImpl
import com.sobolev.beerratings.domain.repository.BookmarksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(repositoryImpl: BookmarksRepositoryImpl): BookmarksRepository
}