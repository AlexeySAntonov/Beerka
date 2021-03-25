package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenData
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.module.injector.ComponentsHolder
import timber.log.Timber

object FeatureDetailsComponentsHolder : ComponentsHolder<FeatureDetailsApi, FeatureDetailsComponentDependencies, FeatureDetailsScreenData> {

  private val componentsMap = hashMapOf<String, FeatureDetailsApi>()
  private val screenDataMap = hashMapOf<String, FeatureDetailsScreenData>()
  private var restorationDependencies: FeatureDetailsComponentDependencies? = null

  override fun init(dependencies: FeatureDetailsComponentDependencies): Pair<FeatureDetailsApi, String> {
    val (component, componentKey) = FeatureDetailsComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    Timber.e("Init component key: $componentKey")
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun setScreenDataAndGetComponent(componentKey: String, screenData: FeatureDetailsScreenData?): Pair<FeatureDetailsApi, String> {
    return componentsMap[componentKey]?.also { screenData?.let { sd -> screenDataMap[componentKey] = sd } }?.to(componentKey)
      ?: restoreComponent(screenData)
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

  override fun getScreenData(componentKey: String): FeatureDetailsScreenData {
    return screenDataMap[componentKey] ?: throw NullPointerException("No screen data was passed for this component!")
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
    screenDataMap.remove(componentKey)
    Timber.e("Reset component key: $componentKey, Components map size: ${componentsMap.size}")
  }

}