package com.aleksejantonov.core.ui.model

import com.aleksejantonov.core.model.FilterModel

data class FilterItem(
  val abvPair: Pair<Float, Float>,
  val ibuPair: Pair<Float, Float>,
  val ebcPair: Pair<Float, Float>,
) {

  companion object {
    fun from(model: FilterModel) = with(model) {
      FilterItem(
        abvPair = abvPair,
        ibuPair = ibuPair,
        ebcPair = ebcPair
      )
    }
  }
}