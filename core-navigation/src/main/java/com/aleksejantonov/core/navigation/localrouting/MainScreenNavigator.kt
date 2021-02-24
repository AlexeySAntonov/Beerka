package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.NavHostActivity
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.navigation.PagerNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.navigation.TabNavigation
import com.aleksejantonov.core.ui.base.BaseFragment
import java.lang.ref.WeakReference

class MainScreenNavigator(
  private val activity: BaseNavHostActivity,
  private val pagerNavigation: PagerNavigation,
  val tabNavigation: TabNavigation,
  private val persistentBottomNavigation: PersistentBottomSheetNavigation
) : Navigator {

  init {
    pagerNavigation.activityRef = WeakReference(activity)
  }

  override fun applyRoute(route: NavigationRoute) {
    when (route) {
      is NavigationRoute.Page -> handlePage(route.index)
      is NavigationRoute.Tab -> handleTab(route)
      is NavigationRoute.FullScreen -> handleFullScreen(route)
      is NavigationRoute.NextScreen -> handleNext(route)
      is NavigationRoute.Back -> handleBack(route.force)
      is NavigationRoute.PersistentBottom -> handleOpenPersistentBottom(route)
      is NavigationRoute.ClosePersistentBottom -> handleClosePersistentBottom()
      else -> throw IllegalArgumentException("Unsupported navigation route: $route")
    }
  }

  override fun currentScreen(): Fragment? {
    return persistentBottomNavigation.currentScreen()
      ?: run {
        if (pagerNavigation.isRoot()) tabNavigation.currentScreen()
        else null
      }
  }

  override fun onRelease() {
    persistentBottomNavigation.release()
  }

  private fun handlePage(index: Int) {
    pagerNavigation.openPage(index)
  }

  private fun handleTab(route: NavigationRoute.Tab) {
    GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
      tabNavigation.switchTab(fragment, route.tab)
    }
  }

  private fun handleFullScreen(route: NavigationRoute.FullScreen) {
    openActivity(activity, NavHostActivity::class, route.screenKey)
  }

  private fun handleNext(route: NavigationRoute.NextScreen) {
    if (persistentBottomNavigation.currentFragments().isNotEmpty()) {
      if (route.ignoreBottomSheetNavigation) {
        applyRoute(NavigationRoute.FullScreen(route.screenKey, route.addToBackStack))
      } else {
        GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
          persistentBottomNavigation.open(fragment, route.addToBackStack)
        }
      }
    } else if (pagerNavigation.isRoot()) {
      GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
        tabNavigation.open(fragment, addToBackStack = route.addToBackStack)
      }
    }
  }

  override fun handleBack(force: Boolean) {
    var result = persistentBottomNavigation.back(force)

    // Back pages
    if (!result && !pagerNavigation.isRoot()) {
      pagerNavigation.resetToRoot()
      result = true
    }

    // Back tabs
    if (!result) {
      result = tabNavigation.back(force)
    } else if (persistentBottomNavigation.currentFragments().isEmpty()) {
      (tabNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
    }

    if (!result) {
      activity.finish()
    }
  }

  private fun handleOpenPersistentBottom(route: NavigationRoute.PersistentBottom) {
    GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
      persistentBottomNavigation.open(fragment, route.addToBackStack)
    }
  }

  private fun handleClosePersistentBottom() {
    persistentBottomNavigation.close()
    (tabNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
  }
}