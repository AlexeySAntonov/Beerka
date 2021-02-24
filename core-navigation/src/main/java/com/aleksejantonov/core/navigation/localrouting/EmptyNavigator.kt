package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.NavigationRoute

object EmptyNavigator : Navigator {
  override fun applyRoute(route: NavigationRoute) {
    // Stub
  }

  override fun currentScreen(): Fragment? = null

  override fun handleBack(force: Boolean) {
    // Stub
  }
}