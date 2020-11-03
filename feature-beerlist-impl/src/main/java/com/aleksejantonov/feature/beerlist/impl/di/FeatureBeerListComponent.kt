package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import dagger.Component

@Component(
    modules = [FeatureBeerListModule::class],
    dependencies = [FeatureBeerListComponentDependencies::class]
)
@RootScope
interface FeatureBeerListComponent : FeatureBeerListApi {


    companion object {

        fun init(dependencies: FeatureBeerListComponentDependencies): FeatureBeerListApi {
            return DaggerFeatureBeerListComponent.builder()
                .featureBeerListComponentDependencies(dependencies)
                .build()

        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureBeerListComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
    fun featureDetailsScreenProvider(): FeatureDetailsScreenProvider
}