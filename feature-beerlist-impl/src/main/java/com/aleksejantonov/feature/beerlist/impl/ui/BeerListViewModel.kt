package com.aleksejantonov.feature.beerlist.impl.ui

import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.feature.beerlist.impl.data.FeatureBeerListScreenInteractor
import javax.inject.Inject

class BeerListViewModel @Inject constructor(
  private val featureBeerListInteractor: FeatureBeerListScreenInteractor
) : BaseViewModel() {

  fun navigateToDetails(id: Long, name: String, imageUrl: String?) {
    AppRouter.openDetailsFeature(ScreenData(id, name, imageUrl))
  }
}