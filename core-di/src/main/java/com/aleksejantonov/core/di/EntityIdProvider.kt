package com.aleksejantonov.core.di

interface EntityIdProvider {
  fun safeSetId(id: Long)
  fun getId(): Long
}