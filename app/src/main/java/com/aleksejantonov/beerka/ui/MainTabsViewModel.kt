package com.aleksejantonov.beerka.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aleksejantonov.beerka.di.DI
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.ui.base.BaseViewModel

class MainTabsViewModel : BaseViewModel() {
  // TODO: Refactor: MainTabsComponent + inject
  private val appRouter by lazy { DI.appComponent.globalRouter() }
  private val featureProvider by lazy { DI.appComponent.globalFeatureProvider() }

  // first -> navigation tab
  // second -> show/hide red dot
  private val _notificationBadgeData = MutableLiveData<List<Pair<NavigationTab, Boolean>>>()
  val notificationBadgeData: LiveData<List<Pair<NavigationTab, Boolean>>> = _notificationBadgeData

  private val _activityBadgeData = MutableLiveData<Boolean>()
  val activityBadgeData: LiveData<Boolean> = _activityBadgeData

  fun onTabClick(tab: NavigationTab, wasSelected: Boolean) {
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
    appRouter.openFilterFeature(context)
  }
}