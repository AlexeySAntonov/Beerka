package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.NavigationRoute

interface LocalRouter {

  fun applyRoute(route: NavigationRoute)

  fun currentScreen(): Fragment?

  fun onRelease() {}

}