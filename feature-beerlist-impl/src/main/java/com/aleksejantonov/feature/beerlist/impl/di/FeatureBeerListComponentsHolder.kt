package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureBeerListComponentsHolder : ComponentsHolder<FeatureBeerListApi, FeatureBeerListComponentDependencies> {

  private var componentsMap = hashMapOf<String, FeatureBeerListApi>()
  private var restorationDependencies: FeatureBeerListComponentDependencies? = null

  override fun init(dependencies: FeatureBeerListComponentDependencies): Pair<FeatureBeerListApi, String> {
    val (component, componentKey) = FeatureBeerListComponent.init(dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun get(componentKey: String): FeatureBeerListApi {
    return componentsMap[componentKey] ?: restoreIfPossible(componentKey)
  }

  override fun restoreIfPossible(componentKey: String): FeatureBeerListApi {
    return FeatureBeerListComponent
      .init(dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .first
      .also { componentsMap[componentKey] = it }
  }

  override fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
  }

}