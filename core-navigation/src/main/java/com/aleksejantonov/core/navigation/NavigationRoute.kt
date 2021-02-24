package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment

sealed class NavigationRoute {

  data class Page(val index: Int) : NavigationRoute()

  data class Tab(
    val screenKey: String,
    val tab: NavigationTab
  ) : NavigationRoute()

  data class FullScreen(
    val screenKey: String,
    val addToBackStack: Boolean = true
  ) : NavigationRoute()

  data class PersistentBottom(
    val screenKey: String,
    val addToBackStack: Boolean = true
  ) : NavigationRoute()

  data class NextScreen(
    val screenKey: String,
    val addToBackStack: Boolean = true,
    val ignoreBottomSheetNavigation: Boolean = false
  ) : NavigationRoute()

  data class Back(val force: Boolean = false) : NavigationRoute()

  object ClosePersistentBottom : NavigationRoute()

  data class ResetWithRoot(val fragmentFactory: () -> Fragment) : NavigationRoute()
}