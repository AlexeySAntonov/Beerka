package com.aleksejantonov.core.db.impl.di

import android.content.Context
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class])
@Singleton
interface DatabaseComponent : CoreDatabaseApi {

    companion object {
//        lateinit var coreDatabaseApi: CoreDatabaseApi

        fun init(context: Context): CoreDatabaseApi {
            return DaggerDatabaseComponent.builder()
                .databaseModule(DatabaseModule(context))
                .build()
        }

//        fun get(): CoreDatabaseApi {
//            return if (::coreDatabaseApi.isInitialized) coreDatabaseApi
//            else throw IllegalStateException("Database must be initialized")
//        }
    }
}