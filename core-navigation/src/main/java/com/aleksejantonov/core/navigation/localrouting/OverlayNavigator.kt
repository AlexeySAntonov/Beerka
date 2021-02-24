package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.NavHostActivity
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.ui.base.BaseFragment

class OverlayNavigator(
  private val activity: BaseNavHostActivity,
  private val fragmentNavigation: FragmentNavigation,
  private val persistentBottomNavigation: PersistentBottomSheetNavigation
) : Navigator {

  override fun applyRoute(route: NavigationRoute) {
    when (route) {
      is NavigationRoute.FullScreen -> handleFullScreen(route)
      is NavigationRoute.NextScreen -> handleNext(route)
      is NavigationRoute.Back -> handleBack(route.force)
      is NavigationRoute.PersistentBottom -> handleOpenPersistentBottom(route)
      is NavigationRoute.ClosePersistentBottom -> handleClosePersistentBottom()
      else -> throw IllegalArgumentException("Unsupported navigation route: $route")
    }
  }

  override fun setInitialScreen(fragment: Fragment) {
    fragmentNavigation.open(fragment, true)
  }

  override fun currentScreen(): Fragment? {
    return persistentBottomNavigation.currentScreen() ?: fragmentNavigation.currentScreen()
  }

  override fun onRelease() {
    persistentBottomNavigation.release()
  }

  override fun handleBack(force: Boolean) {
    var result = persistentBottomNavigation.back(force)

    if (!result) {
      result = fragmentNavigation.back(force)
    } else if (persistentBottomNavigation.currentFragments().isEmpty()) {
      (fragmentNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
    }

    if (!result) {
      activity.finish()
    }
  }

  private fun handleFullScreen(route: NavigationRoute.FullScreen) {
    if (persistentBottomNavigation.currentFragments().isEmpty()) {
      GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
        fragmentNavigation.open(fragment, route.addToBackStack)
      }
    } else {
      openActivity(activity, NavHostActivity::class, route.screenKey)
    }
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
    } else {
      GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
        fragmentNavigation.open(fragment, route.addToBackStack)
      }
    }
  }

  private fun handleOpenPersistentBottom(route: NavigationRoute.PersistentBottom) {
    GlobalFeatureProvider.pop(route.screenKey)?.let { fragment ->
      persistentBottomNavigation.open(fragment, route.addToBackStack)
    }
  }

  private fun handleClosePersistentBottom() {
    persistentBottomNavigation.close()
    (fragmentNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
  }

}