package com.aleksejantonov.module.injector

import java.io.Serializable

interface ComponentsHolder<C : BaseApi, B : BaseDependencies, S : ScreenData> {
  fun init(dependencies: B): Pair<C, String>
  fun setScreenDataAndGetComponent(componentKey: String, screenData: S? = null): Pair<C, String>
  fun restoreComponent(screenData: S? = null): Pair<C, String>
  fun getScreenData(componentKey: String): S
  fun reset(componentKey: String)
}

// Marker
interface BaseApi
// Marker
interface BaseDependencies
// Marker
interface ScreenData : Serializable