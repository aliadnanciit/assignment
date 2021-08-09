package com.assignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {

    companion object {
        @Provides
        @Named("IO_DISPATCHER")
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Named("DEFAULT_DISPATCHER")
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }
}