package com.tsquaredapps.employeedirectory.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tsquaredapps.employeedirectory.repository.EmployeeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkingModule {

    private const val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"

    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    fun provideEmployeeApiService(retrofit: Retrofit): EmployeeApiService =
        retrofit.create(EmployeeApiService::class.java)
}