package com.assignment.di

import android.os.Build
import com.assignment.common.BuildSdkVersionChecker
import com.assignment.repository.HistoryRepository
import com.assignment.repository.HistoryRepositoryImpl
import com.assignment.repository.ShortenUrlRepository
import com.assignment.repository.ShortenUrlRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindShortenUrlRepository(shortenUrlRepositoryImpl: ShortenUrlRepositoryImpl): ShortenUrlRepository

    @Binds
    abstract fun bindHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository

    companion object {
        @Provides
        @Named("IO_DISPATCHER")
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Named("DEFAULT_DISPATCHER")
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @Singleton
        fun provideBuildSdkVersion(): BuildSdkVersionChecker {
            return BuildSdkVersionChecker(Build.VERSION.SDK_INT)
        }
    }
}