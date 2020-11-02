package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.NavHostActivity
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.ui.base.BaseFragment
import hellomobile.hello.core.ui.navigation.LocalRouter


class OverlayLocalRouter(
  private val activity: BaseNavHostActivity,
  private val fragmentNavigation: FragmentNavigation,
  private val persistentBottomNavigation: PersistentBottomSheetNavigation
) : LocalRouter {

  override fun applyRoute(route: NavigationRoute) {
    when (route) {
      is NavigationRoute.PersistentBottom -> persistentBottomNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
      is NavigationRoute.FullScreen -> handleFullScreen(route)
      is NavigationRoute.NextScreen -> handleNext(route)
      is NavigationRoute.Back -> handleBack(route.force)
      is NavigationRoute.ClosePersistentBottom -> handleClosePersistentBottom()
    }
  }

  override fun currentScreen(): Fragment? {
    return persistentBottomNavigation.currentScreen() ?: fragmentNavigation.currentScreen()
  }

  override fun onRelease() {
    persistentBottomNavigation.release()
  }

  private fun handleFullScreen(route: NavigationRoute.FullScreen) {
    if (persistentBottomNavigation.currentFragments().isEmpty()) {
      fragmentNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
    } else {
      AppRouter.openActivity(activity, NavHostActivity::class, route.fragmentFactory.invoke())
    }
  }

  private fun handleNext(route: NavigationRoute.NextScreen) {
    if (persistentBottomNavigation.currentFragments().isNotEmpty()) {
      if (route.ignoreBottomSheetNavigation) {
        applyRoute(NavigationRoute.FullScreen(route.fragmentFactory, route.addToBackStack))
      } else {
        persistentBottomNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
      }
    } else {
      fragmentNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
    }
  }

  private fun handleBack(force: Boolean) {
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

  private fun handleClosePersistentBottom() {
    persistentBottomNavigation.close()
    (fragmentNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
  }

}