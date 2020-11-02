package com.aleksejantonov.core.navigation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigation(
    @IdRes override val containerId: Int,
    private val fragmentManager: FragmentManager,
//    private val transitionProvider: TransitionProvider = CustomTransitionProvider()
) : Navigation {

  fun open(fragment: Fragment, addToBackStack: Boolean/**, transitionProvider: TransitionProvider = this.transitionProvider*/) {
    val currentFragment = currentFragments().lastOrNull()
    fragmentManager.beginTransaction()
//        .applyTransitions(transitionProvider, currentFragment == null)
        .apply {
          if (addToBackStack) {
            addToBackStack(null)
          }
        }
        .replace(containerId, fragment)
        .commitAllowingStateLoss()
  }

  override fun back(force: Boolean): Boolean {
    val currentFragments = currentFragments()
    if (currentFragments.isEmpty()) return false
    val currentFragment = currentFragments.last()
//    if (!force && (currentFragment as? OnBackPressable)?.onBackPressed() == true) {
//      return true
//    }
    val isLast = fragmentManager.backStackEntryCount <= 1
    if (!isLast) {
      fragmentManager.popBackStackImmediate()
      fragmentManager.beginTransaction()
          .remove(currentFragment)
          .commitNowAllowingStateLoss()
    }
    return !isLast
  }

  override fun currentScreen(): Fragment? = currentFragments().lastOrNull()

  private fun currentFragments(): List<Fragment> =
      fragmentManager.fragments.filter { it.id == containerId && it.isAdded }

  fun clear() {
    while (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStackImmediate()
    }
    currentFragments().forEach {
      fragmentManager.beginTransaction()
          .remove(it)
          .commitNowAllowingStateLoss()
    }
  }
}