package com.tsquaredapps.employeedirectory.di

import com.tsquaredapps.employeedirectory.common.DefaultDispatcherProvider
import com.tsquaredapps.employeedirectory.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}