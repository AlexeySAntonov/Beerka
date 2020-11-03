package com.aleksejantonov.beerka.di

import android.app.Application
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.db.impl.di.DatabaseComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

  @Provides
  @Singleton
  fun providesDatabaseApi(): CoreDatabaseApi {
    return DatabaseComponent.init(application.baseContext)
  }
}