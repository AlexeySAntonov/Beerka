package com.aleksejantonov.core.api.di

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.api.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @HttpHost
  fun provideHttpHost(): String = "https://api.punkapi.com/"

  @Provides
  @Singleton
  fun provideGson(): Gson {
    return GsonBuilder()
      .create()
  }

  @Provides
  @Singleton
  @DefaultHttpClient
  fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Platform.get().log(message)
      }
    }).apply {
      level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .apply { if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor()) }
      .build()
  }

  @Provides
  @Singleton
  fun provideBeersApi(@DefaultHttpClient client: OkHttpClient, gson: Gson, @HttpHost host: String): BeersApi =
    BeersApi(createApi(client, gson, host))

  private inline fun <reified T> createApi(client: OkHttpClient, gson: Gson, baseUrl: String): T =
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
      .create(T::class.java)
}