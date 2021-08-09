package com.assignment

import android.app.Application
import com.assignment.usecase.CreateShortURLUseCase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AssignmentApp : Application() {

    @Inject
    lateinit var createShortURLUseCase: CreateShortURLUseCase

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}