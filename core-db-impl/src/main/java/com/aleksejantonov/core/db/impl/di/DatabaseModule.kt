package com.aleksejantonov.core.db.impl.di

import android.content.Context
import androidx.room.Room
import com.aleksejantonov.core.db.api.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.BeersStore
import com.aleksejantonov.core.db.api.store.Cleaner
import com.aleksejantonov.core.db.impl.data.TrDatabaseImpl
import com.aleksejantonov.core.db.impl.store.BeersStoreImpl
import com.aleksejantonov.core.db.impl.store.CleanerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

  @Singleton
  @Provides
  internal fun provideDatabaseClientApi(): DatabaseClientApi {
    return Room.databaseBuilder(context, TrDatabaseImpl::class.java, "true_db")
        .fallbackToDestructiveMigration()
        .build()
  }

  @Singleton
  @Provides
  fun provideCleaner(dbApi: DatabaseClientApi): Cleaner = CleanerImpl(dbApi)

  @Singleton
  @Provides
  fun provideBeersStore(dbApi: DatabaseClientApi): BeersStore = BeersStoreImpl(dbApi)

}