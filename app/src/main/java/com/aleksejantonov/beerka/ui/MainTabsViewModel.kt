package com.aleksejantonov.beerka.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.ui.base.BaseViewModel

class MainTabsViewModel : BaseViewModel() {

  // first -> navigation tab
  // second -> show/hide red dot
  private val _notificationBadgeData = MutableLiveData<List<Pair<NavigationTab, Boolean>>>()
  val notificationBadgeData: LiveData<List<Pair<NavigationTab, Boolean>>> = _notificationBadgeData

  private val _activityBadgeData = MutableLiveData<Boolean>()
  val activityBadgeData: LiveData<Boolean> = _activityBadgeData

  fun onTabClick(tab: NavigationTab, wasSelected: Boolean) {
    when (tab) {
      NavigationTab.BEER_LIST -> {
//        AppRouter.switchTab(
//            rootFactory = { /** BeerListFragment */ },
//            tab = NavigationTab.BEER_LIST
//        )
      }
      NavigationTab.FAVORITES -> {
//          AppRouter.switchTab(
//              rootFactory = { /** FavoriteBeersFragment */ },
//              tab = NavigationTab.FAVORITES
//          )
      }
    }
  }
}