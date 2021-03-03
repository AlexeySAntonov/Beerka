package com.aleksejantonov.beerka.ui

import android.content.Context
import com.aleksejantonov.beerka.di.DI
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.ui.base.BaseViewModel

class MainTabsViewModel : BaseViewModel() {
  // TODO: Refactor: MainTabsComponent + inject
  private val appRouter by lazy { DI.appComponent.globalRouter() }
  private val featureProvider by lazy { DI.appComponent.globalFeatureProvider() }

  fun onTabClick(tab: NavigationTab) {
    when (tab) {
      NavigationTab.BEER_LIST -> {
        appRouter.switchTab(
          screenKey = featureProvider.provideFeatureBeerList(),
          tab = NavigationTab.BEER_LIST
        )
      }
      NavigationTab.FAVORITES -> {
        appRouter.switchTab(
          screenKey = featureProvider.provideFeatureFavorites(),
          tab = NavigationTab.FAVORITES
        )
      }
    }
  }

  fun openFilterFeature(context: Context) {
    appRouter.openFilterModal(context)
  }
}