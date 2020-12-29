package com.aleksejantonov.core.navigation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.navigation.R
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.mvvm.requireValue

class TabNavigation(
    private val hostFragment: () -> Fragment?
) : Navigation {

  override val containerId: Int = 0 //Do not use

  private val _currentTab = MutableLiveData(NavigationTab.BEER_LIST)
  val currentTab: LiveData<NavigationTab> = _currentTab

//  private val transitionProvider = TabTransitionProvider()

  fun switchTab(
      rootFactory: () -> Fragment,
      tab: NavigationTab
  ) {
    val fm = fragmentManager(tab) ?: return
    if (fm.backStackEntryCount >= 1) { //tab contains at least one screen
      when {
        _currentTab.value != tab -> {  //just switch
//          (currentTabScreen(currentTab.requireValue()) as? OnShowFragmentListener)?.onShow(false) //hide screen
          _currentTab.postValue(tab)
//          (currentTabScreen(tab) as? OnShowFragmentListener)?.onShow(true) //show screen
        }
        fm.backStackEntryCount > 1 -> { //reset current tab to its root screen (click on already selected tab)
          while (fm.backStackEntryCount > 1) {
            fm.popBackStackImmediate()
          }
        }
        else -> (currentScreen() as? TabRootScreen)?.onReselect() //click on tab with opened root screen again
      }
    } else {
//      (currentTabScreen(currentTab.requireValue()) as? OnShowFragmentListener)?.onShow(false) //hide screen
      open(rootFactory, tab) //tab to open does not contain any screens
    }
  }

  fun open(
      fragmentFactory: () -> Fragment,
      tab: NavigationTab = currentTab.requireValue(),
      addToBackStack: Boolean = true
  ) {
    val fm = fragmentManager(tab) ?: return
    val fragment = fragmentFactory.invoke()
    fragment.arguments = (fragment.arguments ?: Bundle()).apply {
      putBoolean(BaseFragment.ARG_IS_IN_TAB_NAVIGATION, true)
    }
    fm.beginTransaction()
//        .applyTransitions(transitionProvider, fm.backStackEntryCount == 0)
        .apply {
          if (addToBackStack) {
            addToBackStack(null)
          }
        }
        .replace(R.id.navContainer, fragment)
        .commitAllowingStateLoss()
    if (currentTab.requireValue() != tab) {
      _currentTab.postValue(tab)
    }
  }

  override fun back(force: Boolean): Boolean {
    if (isRoot()) {
      resetTab(NavigationTab.FAVORITES)
      return false
    }
    val fm = fragmentManager(currentTab.requireValue()) ?: return false
    val isLast = fm.backStackEntryCount == 1
    if (isLast) {
      _currentTab.postValue(NavigationTab.BEER_LIST)
    } else {
      val current = currentScreen()
      fm.popBackStackImmediate()
      current?.let {
        fm.beginTransaction()
            .remove(it)
            .commitNowAllowingStateLoss()
      }
    }
    return true
  }

  fun resetToRoot() {
    resetTab(NavigationTab.FAVORITES)
    fragmentManager(NavigationTab.BEER_LIST)?.apply {
      while (backStackEntryCount > 1) {
        popBackStackImmediate()
      }
    }
    if (currentTab.value != NavigationTab.BEER_LIST) {
      _currentTab.postValue(NavigationTab.BEER_LIST)
    }
  }

  private fun resetTab(tab: NavigationTab) {
    fragmentManager(tab)?.apply {
      while (backStackEntryCount > 0) {
        popBackStackImmediate()
      }
    }
  }

  private fun isRoot() = currentTab.requireValue() == NavigationTab.BEER_LIST && currentTabSize() <= 1

  override fun currentScreen(): Fragment? = currentTabScreen(currentTab.requireValue())

  private fun currentTabScreen(tab: NavigationTab): Fragment? =
      fragmentManager(tab)
          ?.fragments
          ?.lastOrNull { it.isAdded }

  private fun currentTabSize(): Int =
      fragmentManager(currentTab.requireValue())
          ?.backStackEntryCount ?: 0

  private fun fragmentManager(tab: NavigationTab): FragmentManager? =
      hostFragment()?.childFragmentManager?.fragments
          ?.firstOrNull { it.tag == tab.name && it.isAdded }
          ?.childFragmentManager

  interface TabRootScreen {
    fun onReselect()
  }
}