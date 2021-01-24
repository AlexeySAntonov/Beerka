package com.aleksejantonov.core.di

object ComponentsManager {

  private val componentsMap = hashMapOf<String, Any>()

  @Synchronized
  fun save(key: String, component: Any) {
    componentsMap[key] = component
  }

  @Synchronized
  fun <T> get(key: String): T? {
    return componentsMap[key] as? T
  }

  @Synchronized
  fun release(key: String) {
    componentsMap.remove(key)
  }
}