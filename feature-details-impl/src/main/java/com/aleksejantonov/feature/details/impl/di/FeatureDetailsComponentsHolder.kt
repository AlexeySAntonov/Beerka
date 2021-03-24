package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureDetailsComponentsHolder : ComponentsHolder<FeatureDetailsApi, FeatureDetailsComponentDependencies> {

  private var componentsMap = hashMapOf<String, FeatureDetailsApi>()
  private var restorationDependencies: FeatureDetailsComponentDependencies? = null

  override fun init(dependencies: FeatureDetailsComponentDependencies): Pair<FeatureDetailsApi, String> {
    val (component, componentKey) = FeatureDetailsComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun get(componentKey: String): FeatureDetailsApi {
    return componentsMap[componentKey] ?: restoreIfPossible(componentKey)
  }

  override fun restoreIfPossible(componentKey: String): FeatureDetailsApi {
    return FeatureDetailsComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .first
      .also { componentsMap[componentKey] = it }
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
  }

}