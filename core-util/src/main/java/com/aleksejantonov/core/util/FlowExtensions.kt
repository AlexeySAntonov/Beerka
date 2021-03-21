package com.aleksejantonov.core.util

import kotlinx.coroutines.flow.SharedFlow

fun <T> SharedFlow<T>.value(index: Int): T? {
  return replayCache.getOrNull(index)
}