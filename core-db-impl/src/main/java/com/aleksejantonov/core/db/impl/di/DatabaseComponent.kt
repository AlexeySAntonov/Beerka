package com.aleksejantonov.core.db.impl.di

import android.content.Context
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class])
@Singleton
interface DatabaseComponent : CoreDatabaseApi {

    companion object {
        fun init(context: Context): CoreDatabaseApi {
            return DaggerDatabaseComponent.builder()
                .databaseModule(DatabaseModule(context))
                .build()
        }
    }
}