package com.aleksejantonov.beerka.ui.base

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.NavHostActivity
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.navigation.PagerNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.navigation.TabNavigation
import com.aleksejantonov.core.ui.base.BaseFragment
import hellomobile.hello.core.ui.navigation.LocalRouter
import java.lang.ref.WeakReference

class MainScreenLocalRouter(
  private val activity: BaseNavHostActivity,
  private val pagerNavigation: PagerNavigation,
  val tabNavigation: TabNavigation,
  private val persistentBottomNavigation: PersistentBottomSheetNavigation
) : LocalRouter {

  init {
    pagerNavigation.activityRef = WeakReference(activity)
  }

  override fun applyRoute(route: NavigationRoute) {
    when (route) {
      is NavigationRoute.Page -> pagerNavigation.openPage(route.index)
//      is NavigationRoute.Tab -> tabNavigation.switchTab(route.fragmentFactory, route.id)
//      is NavigationRoute.PersistentBottom -> persistentBottomNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
      is NavigationRoute.FullScreen -> handleFullScreen(route)
      is NavigationRoute.NextScreen -> handleNext(route)
      is NavigationRoute.Back -> handleBack(route.force)
      is NavigationRoute.ClosePersistentBottom -> handleClosePersistentBottom()
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

  private fun handleFullScreen(route: NavigationRoute.FullScreen) {
    AppRouter.openActivity(activity, NavHostActivity::class, route.fragmentFactory.invoke())
  }

  private fun handleNext(route: NavigationRoute.NextScreen) {
    if (persistentBottomNavigation.currentFragments().isNotEmpty()) {
      if (route.ignoreBottomSheetNavigation) {
        applyRoute(NavigationRoute.FullScreen(route.fragmentFactory, route.addToBackStack))
      } else {
        persistentBottomNavigation.open(route.fragmentFactory.invoke(), route.addToBackStack)
      }
    } else if (pagerNavigation.isRoot()) {
      tabNavigation.open(route.fragmentFactory, addToBackStack = route.addToBackStack)
    }
  }

  private fun handleBack(force: Boolean) {
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

  private fun handleClosePersistentBottom() {
    persistentBottomNavigation.close()
    (tabNavigation.currentScreen() as? BaseFragment)?.onReturnToScreen()
  }
}