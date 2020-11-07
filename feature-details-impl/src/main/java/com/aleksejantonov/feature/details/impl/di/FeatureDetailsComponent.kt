package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import dagger.Component

@Component(
    modules = [FeatureDetailsModule::class],
    dependencies = [FeatureDetailsComponentDependencies::class]
)
@FeatureScope
interface FeatureDetailsComponent : FeatureDetailsApi, ViewModelFactoryProvider {

    companion object {

        fun init(dependencies: FeatureDetailsComponentDependencies): FeatureDetailsApi {
            return DaggerFeatureDetailsComponent.builder()
                .featureDetailsComponentDependencies(dependencies)
                .build()

        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureDetailsComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
}