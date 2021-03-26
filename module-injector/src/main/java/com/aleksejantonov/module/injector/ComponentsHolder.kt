package com.aleksejantonov.module.injector

import timber.log.Timber
import java.io.Serializable

abstract class ComponentsHolder<C : BaseApi, B : BaseDependencies, S : ScreenData> {
  protected val componentsMap: HashMap<String, C> = hashMapOf()
  protected val screenDataMap: HashMap<String, S> = hashMapOf()
  protected var restorationDependencies: B? = null

  abstract fun initComponent(dependencies: B): Pair<C, String>
  abstract fun restoreComponent(screenData: S? = null): Pair<C, String>

  fun setScreenDataAndGetComponent(componentKey: String, screenData: S? = null): Pair<C, String> {
    return componentsMap[componentKey]?.also { screenData?.let { sd -> screenDataMap[componentKey] = sd } }?.to(componentKey)
      ?: restoreComponent(screenData)
  }

  fun getScreenData(componentKey: String): S {
    return screenDataMap[componentKey] ?: throw NullPointerException("No screen data was passed for this component!")
  }

  fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
    screenDataMap.remove(componentKey)
    Timber.e("Reset component key: $componentKey, Components map size: ${componentsMap.size}, ScreenData map size: ${screenDataMap.size}")
  }
}

// Marker
interface BaseApi

// Marker
interface BaseDependencies

// Marker
interface ScreenData : Serializable