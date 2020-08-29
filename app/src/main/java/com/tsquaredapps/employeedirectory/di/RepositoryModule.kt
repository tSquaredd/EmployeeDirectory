package com.tsquaredapps.employeedirectory.di

import com.tsquaredapps.employeedirectory.repository.EmployeeApi
import com.tsquaredapps.employeedirectory.repository.EmployeeApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEmployeeApi(employeeApiImpl: EmployeeApiImpl): EmployeeApi

}