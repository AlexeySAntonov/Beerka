package com.aleksejantonov.feature.details.next.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.details.next.api.di.FeatureDetailsNextApi
import dagger.Component

@Component(
    modules = [FeatureDetailsNextModule::class],
    dependencies = [FeatureDetailsNextComponentDependencies::class]
)
@FeatureScope
interface FeatureDetailsNextComponent : FeatureDetailsNextApi, ViewModelFactoryProvider {

    companion object {

        fun init(dependencies: FeatureDetailsNextComponentDependencies): FeatureDetailsNextApi {
            return DaggerFeatureDetailsNextComponent.builder()
                .featureDetailsNextComponentDependencies(dependencies)
                .build()

        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureDetailsNextComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
}