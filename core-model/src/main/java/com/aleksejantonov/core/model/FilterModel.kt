package com.aleksejantonov.core.model

data class FilterModel(
  val abvPair: Pair<Float, Float>,
  val ibuPair: Pair<Float, Float>,
  val ebcPair: Pair<Float, Float>,
) {

  companion object {
    const val ABV_MIN = 0f
    const val ABV_MAX = 60f
    const val IBU_MIN = 0f
    const val IBU_MAX = 200f
    const val EBC_MIN = 0f
    const val EBC_MAX = 80f

    fun default() = FilterModel(
      abvPair = Pair(ABV_MIN, ABV_MAX),
      ibuPair = Pair(IBU_MIN, IBU_MAX),
      ebcPair = Pair(EBC_MIN, EBC_MAX),
    )
  }
}