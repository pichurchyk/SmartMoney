package com.example.smartmoney.di.module

import com.example.data.repository.RepositoryImpl
import com.example.data.source.SharedPreferencesSource
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepositories(
        sharedPref: SharedPreferencesSource
    ): Repository = RepositoryImpl(sharedPref)
}