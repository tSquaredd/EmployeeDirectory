package com.tsquaredapps.employeedirectory.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tsquaredapps.employeedirectory.repository.EmployeeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkingModule {

    private const val BASE_URL = "https://s3.amazonaws.com"
    private const val CACHE_FILE_NAME = "http_cache"

    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .cache(
                Cache(
                    directory = File(context.cacheDir, CACHE_FILE_NAME),
                    maxSize = 50L * 1024L * 1024L // 10 MiB
                )
            )
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
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