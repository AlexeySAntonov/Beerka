package com.aleksejantonov.feature.filter.impl.di

import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureFilterComponentsHolder : ComponentsHolder<FeatureFilterApi, FeatureFilterComponentDependencies> {

  private var componentsMap = hashMapOf<String, FeatureFilterApi>()
  private var restorationDependencies: FeatureFilterComponentDependencies? = null

  override fun init(dependencies: FeatureFilterComponentDependencies): Pair<FeatureFilterApi, String> {
    val (component, componentKey) = FeatureFilterComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun get(componentKey: String): FeatureFilterApi {
    return componentsMap[componentKey] ?: restoreIfPossible(componentKey)
  }

  override fun restoreIfPossible(componentKey: String): FeatureFilterApi {
    return FeatureFilterComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .first
      .also { componentsMap[componentKey] = it }
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
  }

}