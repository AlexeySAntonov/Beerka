package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureFavoritesComponentsHolder : ComponentsHolder<FeatureFavoritesApi, FeatureFavoritesComponentDependencies> {

  private var componentsMap = hashMapOf<String, FeatureFavoritesApi>()
  private var restorationDependencies: FeatureFavoritesComponentDependencies? = null

  override fun init(dependencies: FeatureFavoritesComponentDependencies): Pair<FeatureFavoritesApi, String> {
    val (component, componentKey) = FeatureFavoritesComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun get(componentKey: String): FeatureFavoritesApi {
    return componentsMap[componentKey] ?: restoreIfPossible(componentKey)
  }

  override fun restoreIfPossible(componentKey: String): FeatureFavoritesApi {
    return FeatureFavoritesComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .first
      .also { componentsMap[componentKey] = it }
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
  }

}