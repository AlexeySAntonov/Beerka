package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData

object FeatureBeerListComponentsHolder : ComponentsHolder<FeatureBeerListApi, FeatureBeerListComponentDependencies, ScreenData>() {

  override fun init(dependencies: FeatureBeerListComponentDependencies): Pair<FeatureBeerListApi, String> {
    val (component, componentKey) = FeatureBeerListComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureBeerListApi, String> {
    return FeatureBeerListComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        screenData?.let { sd -> screenDataMap[restoredComponentKey] = sd }
      }
  }

}