package com.aleksejantonov.core.ui.model

import com.aleksejantonov.core.model.FilterModel

data class FilterItem(
  val abvPair: Pair<Float, Float>,
  val ibuPair: Pair<Float, Float>,
  val ebcPair: Pair<Float, Float>,
) {

  companion object {
    const val ABV_UI_MIN = 0f
    const val ABV_UI_MAX = 60f
    const val IBU_UI_MIN = 0f
    const val IBU_UI_MAX = 200f
    const val EBC_UI_MIN = 0f
    const val EBC_UI_MAX = 80f

    fun from(model: FilterModel) = with(model) {
      // On the one hand, no need to display huge ranges which can lead to an inconvenient selection.
      FilterItem(
        abvPair = abvPair.let { if (it.second >= ABV_UI_MAX) it.first to ABV_UI_MAX else it },
        ibuPair = ibuPair.let { if (it.second >= IBU_UI_MAX) it.first to IBU_UI_MAX else it },
        ebcPair = ebcPair.let { if (it.second >= EBC_UI_MAX) it.first to EBC_UI_MAX else it }
      )
    }

    fun FilterItem.model() = FilterModel(
      // On the other hand, if a user selects a max UI range value, will use a max business value to embrace more server beers.
      abvPair = abvPair.let { if (it.second == ABV_UI_MAX) it.first to FilterModel.ABV_MAX else it },
      ibuPair = ibuPair.let { if (it.second == IBU_UI_MAX) it.first to FilterModel.IBU_MAX else it },
      ebcPair = ebcPair.let { if (it.second == EBC_UI_MAX) it.first to FilterModel.EBC_MAX else it }
    )
  }
}