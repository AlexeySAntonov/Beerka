package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData

object FeatureFavoritesComponentsHolder : ComponentsHolder<FeatureFavoritesApi, FeatureFavoritesComponentDependencies, ScreenData> {

  private var componentsMap = hashMapOf<String, FeatureFavoritesApi>()
  private val screenDataMap = hashMapOf<String, ScreenData>()
  private var restorationDependencies: FeatureFavoritesComponentDependencies? = null

  override fun init(dependencies: FeatureFavoritesComponentDependencies): Pair<FeatureFavoritesApi, String> {
    val (component, componentKey) = FeatureFavoritesComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun setScreenDataAndGetComponent(componentKey: String, screenData: ScreenData?): Pair<FeatureFavoritesApi, String> {
    return componentsMap[componentKey]?.also { screenData?.let { sd -> screenDataMap[componentKey] = sd } }?.to(componentKey)
      ?: restoreComponent(screenData)
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureFavoritesApi, String> {
    return FeatureFavoritesComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        screenData?.let { sd -> screenDataMap[restoredComponentKey] = sd }
      }
  }

  override fun getScreenData(componentKey: String): ScreenData {
    return screenDataMap[componentKey] ?: throw NullPointerException("No screen data was passed for this component!")
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
  }

}