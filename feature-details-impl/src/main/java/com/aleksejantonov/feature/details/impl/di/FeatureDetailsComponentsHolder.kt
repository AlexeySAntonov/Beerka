package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenData
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.module.injector.ComponentsHolder
import timber.log.Timber

object FeatureDetailsComponentsHolder : ComponentsHolder<FeatureDetailsApi, FeatureDetailsComponentDependencies, FeatureDetailsScreenData>() {

  override fun init(dependencies: FeatureDetailsComponentDependencies): Pair<FeatureDetailsApi, String> {
    val (component, componentKey) = FeatureDetailsComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    Timber.e("Init component key: $componentKey")
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(screenData: FeatureDetailsScreenData?): Pair<FeatureDetailsApi, String> {
    return FeatureDetailsComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        Timber.e("Restored component key: $restoredComponentKey")
        componentsMap[restoredComponentKey] = restoredComponent
        screenData?.let { sd -> screenDataMap[restoredComponentKey] = sd }
      }
  }

}