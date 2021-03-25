package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ScreenData
import timber.log.Timber

object FeatureBeerListComponentsHolder : ComponentsHolder<FeatureBeerListApi, FeatureBeerListComponentDependencies, ScreenData> {

  private var componentsMap = hashMapOf<String, FeatureBeerListApi>()
  private val screenDataMap = hashMapOf<String, ScreenData>()
  private var restorationDependencies: FeatureBeerListComponentDependencies? = null

  override fun init(dependencies: FeatureBeerListComponentDependencies): Pair<FeatureBeerListApi, String> {
    val (component, componentKey) = FeatureBeerListComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun setScreenDataAndGetComponent(componentKey: String, screenData: ScreenData?): Pair<FeatureBeerListApi, String> {
    return componentsMap[componentKey]?.also { screenData?.let { sd -> screenDataMap[componentKey] = sd } }?.to(componentKey)
      ?: restoreComponent(screenData)
  }

  override fun restoreComponent(screenData: ScreenData?): Pair<FeatureBeerListApi, String> {
    return FeatureBeerListComponent
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