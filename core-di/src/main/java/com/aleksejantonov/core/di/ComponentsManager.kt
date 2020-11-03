package com.aleksejantonov.core.di

object ComponentsManager {

  private val componentsMap = hashMapOf<Long, Any>()

  fun save(key: Long, component: Any)
}