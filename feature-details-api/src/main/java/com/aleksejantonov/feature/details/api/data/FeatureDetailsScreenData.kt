package com.aleksejantonov.feature.details.api.data

import com.aleksejantonov.module.injector.ScreenCustomDependencies
import java.io.Serializable

class FeatureDetailsScreenData(
  val beerId: Long
) : Serializable {

  companion object {

    fun from(customDependencies: ScreenCustomDependencies):FeatureDetailsScreenData = with(customDependencies) {
      FeatureDetailsScreenData(
        beerId = (args.getOrNull(0) as? Long) ?: throw IllegalArgumentException("FeatureDetailsScreenData: beerId was not passed!")
      )
    }

    fun default() = FeatureDetailsScreenData(beerId = 0L)
  }
}