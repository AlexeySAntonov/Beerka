package com.aleksejantonov.feature.filter.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import dagger.Component

@Component(
  modules = [FeatureFilterModule::class],
  dependencies = [FeatureFilterComponentDependencies::class]
)
@FeatureScope
interface FeatureFilterComponent : FeatureFilterApi, ViewModelFactoryProvider {

  companion object {

    fun init(dependencies: FeatureFilterComponentDependencies): FeatureFilterApi {
      return DaggerFeatureFilterComponent.builder()
        .featureFilterComponentDependencies(dependencies)
        .build()

    }
  }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureFilterComponentDependencies {
  fun coreDatabaseApi(): CoreDatabaseApi
}