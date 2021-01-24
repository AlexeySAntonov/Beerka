package com.aleksejantonov.feature.filter.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import dagger.BindsInstance
import dagger.Component
import java.util.*

@Component(modules = [FeatureFilterModule::class])
@FeatureScope
interface FeatureFilterComponent : FeatureFilterApi, ViewModelFactoryProvider {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun componentKey(@ComponentKey componentKey: String): Builder

    @BindsInstance
    fun coreDatabaseApi(coreDatabaseApi: CoreDatabaseApi): Builder

    fun build(): FeatureFilterComponent
  }

  companion object {

    fun init(dependencies: FeatureFilterComponentDependencies): Pair<FeatureFilterApi, String> {
      val componentKey = UUID.randomUUID().toString()
      return DaggerFeatureFilterComponent.builder()
        .componentKey(componentKey)
        .coreDatabaseApi(dependencies.coreDatabaseApi())
        .build()
        .also { ComponentsManager.save(componentKey, it) } to componentKey
    }
  }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureFilterComponentDependencies {
  fun coreDatabaseApi(): CoreDatabaseApi
}