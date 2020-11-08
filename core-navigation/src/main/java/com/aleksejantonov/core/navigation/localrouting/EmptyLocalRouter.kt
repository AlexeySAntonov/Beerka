package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.NavigationRoute

object EmptyLocalRouter : LocalRouter {
  override fun applyRoute(route: NavigationRoute) {
    //Stub
  }

  override fun currentScreen(): Fragment? = null
}