package com.aleksejantonov.core.model

data class FilterModel(
  val abvPair: Pair<Float, Float> = Pair(0f, 60f),
  val ibuPair: Pair<Float, Float> = Pair(0f, 200f),
  val ebcPair: Pair<Float, Float> = Pair(0f, 80f),
) {

  companion object {
    fun default() = FilterModel(
      abvPair = Pair(0f, 60f),
      ibuPair = Pair(0f, 200f),
      ebcPair = Pair(0f, 80f),
    )
  }
}