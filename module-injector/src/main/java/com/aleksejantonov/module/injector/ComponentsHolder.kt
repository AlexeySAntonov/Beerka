package com.aleksejantonov.module.injector

interface ComponentsHolder<C : BaseApi, B : BaseDependencies> {
  fun init(dependencies: B): Pair<C, String>
  fun get(componentKey: String): C
  fun restoreIfPossible(componentKey: String): C
  fun reset(componentKey: String)
}

// Marker
interface BaseApi
// Marker
interface BaseDependencies