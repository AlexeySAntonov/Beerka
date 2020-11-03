package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import dagger.Component

@Component(
    modules = [FeatureFavoritesModule::class],
    dependencies = [FeatureFavoritesComponentDependencies::class]
)
@FeatureScope
interface FeatureFavoritesComponent : FeatureFavoritesApi {

    companion object {

        fun init(dependencies: FeatureFavoritesComponentDependencies): FeatureFavoritesApi {
            return DaggerFeatureFavoritesComponent.builder()
                .featureFavoritesComponentDependencies(dependencies)
                .build()

        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureFavoritesComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
}