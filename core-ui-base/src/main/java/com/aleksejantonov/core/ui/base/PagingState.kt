package com.aleksejantonov.core.ui.base

data class PagingState<Entity>(
  val data: List<Entity>,
  val allLoadedStart: Boolean = false,
  val allLoadedEnd: Boolean = false
) {
  fun itemCount() = data.size
}