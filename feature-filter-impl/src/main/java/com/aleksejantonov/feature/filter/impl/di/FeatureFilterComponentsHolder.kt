package com.aleksejantonov.feature.filter.impl.di

import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData

object FeatureFilterComponentsHolder : ComponentsHolder<FeatureFilterApi, FeatureFilterComponentDependencies, ScreenData> {

  private var componentsMap = hashMapOf<String, FeatureFilterApi>()
  private val screenDataMap = hashMapOf<String, ScreenData>()
  private var restorationDependencies: FeatureFilterComponentDependencies? = null

  override fun init(dependencies: FeatureFilterComponentDependencies): Pair<FeatureFilterApi, String> {
    val (component, componentKey) = FeatureFilterComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun setScreenDataAndGetComponent(componentKey: String, screenData: ScreenData?): Pair<FeatureFilterApi, String> {
    return componentsMap[componentKey]?.also { screenData?.let { sd -> screenDataMap[componentKey] = sd } }?.to(componentKey)
      ?: restoreComponent(screenData)
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureFilterApi, String> {
    return FeatureFilterComponent
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