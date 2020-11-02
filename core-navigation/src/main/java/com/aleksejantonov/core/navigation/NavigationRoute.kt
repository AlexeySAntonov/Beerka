package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment

sealed class NavigationRoute {

  data class Page(val index: Int) : NavigationRoute()

  data class FullScreen(
      val fragmentFactory: () -> Fragment,
      val addToBackStack: Boolean = true
  ) : NavigationRoute()

  data class PersistentBottom(
      val fragmentFactory: () -> Fragment,
      val addToBackStack: Boolean = true
  ) : NavigationRoute()

  data class NextScreen(
      val fragmentFactory: () -> Fragment,
      val addToBackStack: Boolean = true,
      val ignoreBottomSheetNavigation: Boolean = false
  ) : NavigationRoute()

  data class Back(val force: Boolean = false) : NavigationRoute()

  object ClosePersistentBottom : NavigationRoute()

  data class ResetWithRoot(val fragmentFactory: () -> Fragment) : NavigationRoute()
}