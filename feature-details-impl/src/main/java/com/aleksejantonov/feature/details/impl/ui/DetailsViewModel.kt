package com.aleksejantonov.feature.details.impl.ui

import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor() : BaseViewModel() {

  fun openNext(screenData: ScreenData) {
    AppRouter.openDetailsNextFeature(screenData)
  }
}