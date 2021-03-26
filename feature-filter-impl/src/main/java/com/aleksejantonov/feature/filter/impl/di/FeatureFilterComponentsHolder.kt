package com.aleksejantonov.feature.filter.impl.di

import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData

object FeatureFilterComponentsHolder : ComponentsHolder<FeatureFilterApi, FeatureFilterComponentDependencies, ScreenData>() {

  override fun initComponent(dependencies: FeatureFilterComponentDependencies): Pair<FeatureFilterApi, String> {
    val (component, componentKey) = FeatureFilterComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureFilterApi, String> {
    return FeatureFilterComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        screenData?.let { sd -> screenDataMap[restoredComponentKey] = sd }
      }
  }

}