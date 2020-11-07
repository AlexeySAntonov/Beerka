package com.aleksejantonov.feature.favorites.impl.ui

import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import javax.inject.Inject

class FavoritesViewModel @Inject constructor() :  BaseViewModel() {

  fun navigateToDetails(screenData: ScreenData) {
    AppRouter.openDetailsFeature(screenData)
  }
}