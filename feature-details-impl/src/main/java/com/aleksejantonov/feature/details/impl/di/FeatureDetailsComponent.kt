package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import dagger.BindsInstance
import dagger.Component
import java.util.*

@Component(modules = [FeatureDetailsModule::class])
@FeatureScope
interface FeatureDetailsComponent : FeatureDetailsApi, ViewModelFactoryProvider {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun componentKey(@ComponentKey componentKey: String): Builder

    @BindsInstance
    fun coreDatabaseApi(coreDatabaseApi: CoreDatabaseApi): Builder

    fun build(): FeatureDetailsComponent
  }

  companion object {

    fun init(dependencies: FeatureDetailsComponentDependencies): Pair<FeatureDetailsApi, String> {
      val componentKey = UUID.randomUUID().toString()
      return DaggerFeatureDetailsComponent.builder()
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
interface FeatureDetailsComponentDependencies {
  fun coreDatabaseApi(): CoreDatabaseApi
}