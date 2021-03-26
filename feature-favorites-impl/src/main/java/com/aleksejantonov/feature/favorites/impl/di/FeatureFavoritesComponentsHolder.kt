package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData

object FeatureFavoritesComponentsHolder : ComponentsHolder<FeatureFavoritesApi, FeatureFavoritesComponentDependencies, ScreenData>() {

  override fun initComponent(dependencies: FeatureFavoritesComponentDependencies): Pair<FeatureFavoritesApi, String> {
    val (component, componentKey) = FeatureFavoritesComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureFavoritesApi, String> {
    return FeatureFavoritesComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        screenData?.let { sd -> screenDataMap[restoredComponentKey] = sd }
      }
  }

}