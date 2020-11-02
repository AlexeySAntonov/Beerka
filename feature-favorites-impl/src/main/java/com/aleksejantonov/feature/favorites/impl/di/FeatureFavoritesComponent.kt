package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [FeatureFavoritesModule::class],
    dependencies = [FeatureFavoritesComponentDependencies::class]
)
@Singleton
interface MediaPlayerComponent : FeatureFavoritesApi {

    companion object {

    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureFavoritesComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
}