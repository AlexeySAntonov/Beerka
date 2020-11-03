package com.aleksejantonov.feature.beerlist.impl.ui

import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import javax.inject.Inject

class BeerListViewModel @Inject constructor(
  private val featureDetailsScreenProvider: FeatureDetailsScreenProvider
) : BaseViewModel() {

  fun navigateToDetails() {
    AppRouter.openFullScreen(featureDetailsScreenProvider.screen())
  }
}