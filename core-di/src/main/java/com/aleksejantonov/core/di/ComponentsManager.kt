package com.aleksejantonov.core.di

object ComponentsManager {

  private val componentsMap = hashMapOf<Long, Any>()

  @Synchronized
  fun save(key: Long, component: Any) {
    componentsMap[key] = component
  }

  @Synchronized
  fun <T> get(key: Long): T {
    return requireNotNull(componentsMap[key] as? T)
  }

  @Synchronized
  fun release(key: Long) {
    componentsMap.remove(key)
  }
}