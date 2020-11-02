package com.aleksejantonov.core.navigation.navigation

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

class PersistentBottomSheetNavigation @JvmOverloads constructor(
    @IdRes override val containerId: Int,
//    private val host: Fragment,
//    private val transitionProvider: TransitionProvider = PersistentBottomSheetTransitionProvider()
) : Navigation {

//  private val dimView: View? get() = host.view?.findViewById(R.id.dimBackgroundView)
//  private val behavior: BottomSheetBehavior<View>?
//    get() = host.view?.findViewById<View>(R.id.navContainer)?.let {
//      BottomSheetBehavior.from(it).also { bottomSheetBehavior -> bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback) }
//    }
//  private val fragmentManager = host.childFragmentManager
//
//  private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
//    override fun onSlide(bottomSheet: View, slideOffset: Float) {
//      dimView?.alpha = slideOffset
//    }
//
//    override fun onStateChanged(bottomSheet: View, state: Int) {
//      when (state) {
//        BottomSheetBehavior.STATE_COLLAPSED,
//        BottomSheetBehavior.STATE_HIDDEN -> {
//          currentFragments().lastOrNull()?.hideKeyboard()
//          onClose.invoke()
//          dimView?.isClickable = false
//        }
//        BottomSheetBehavior.STATE_DRAGGING -> {
//          dimView?.isClickable = true
//          currentFragments().lastOrNull()?.hideKeyboard()
//        }
//        BottomSheetBehavior.STATE_EXPANDED,
//        BottomSheetBehavior.STATE_HALF_EXPANDED,
//        BottomSheetBehavior.STATE_SETTLING -> {
//          dimView?.isClickable = true
//        }
//      }
//    }
//  }

  private val onClose: () -> Unit = {
//    while (fragmentManager.backStackEntryCount > 0) {
//      fragmentManager.popBackStackImmediate()
//    }
//    currentFragments().forEach {
//      fragmentManager.beginTransaction()
//          .remove(it)
//          .commitNowAllowingStateLoss()
//    }
  }

  init {
//    host.requireView().findViewById<ViewGroup>(containerId).setSize(height = persistentBottomSheetHeight(host.requireContext()))
  }

  fun open(fragment: Fragment, addToBackStack: Boolean) {
//    val currentFragment = currentFragments().lastOrNull()
//    behavior?.state = BottomSheetBehavior.STATE_EXPANDED
//    (behavior as? LockableBottomSheetBehavior)?.isLocked = fragment is Lockable
//    if (currentFragment == null) {
//      fragmentManager.beginTransaction()
//          .replace(containerId, fragment)
//          .apply { if (addToBackStack) addToBackStack(null) }
//          .commitAllowingStateLoss()
//    } else {
//      currentFragment.hideKeyboard()
//      fragmentManager.beginTransaction()
//          .applyTransitions(transitionProvider, !addToBackStack)
//          .apply {
//            if (addToBackStack) {
//              addToBackStack(null)
//            }
//          }
//          .replace(containerId, fragment)
//          .commitAllowingStateLoss()
//    }
  }

  override fun back(force: Boolean): Boolean {
    return false
//    val currentFragment = currentFragments().lastOrNull() ?: return false
//    if (!force && (currentFragment as? OnBackPressable)?.onBackPressed() == true) {
//      return true
//    }
//    currentFragment.hideKeyboard()
//    if (fragmentManager.backStackEntryCount <= 1) {
//      behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
//    } else {
//      fragmentManager.beginTransaction()
//          .remove(currentFragment)
//          .commitAllowingStateLoss()
//      fragmentManager.popBackStackImmediate()
//      (behavior as? LockableBottomSheetBehavior)?.isLocked = currentFragments().lastOrNull() is Lockable
//    }
//    return true
  }

  override fun currentScreen(): Fragment? = currentFragments().lastOrNull()

  fun currentFragments(): List<Fragment> = emptyList()
//      fragmentManager.fragments.filter { it.id == containerId }

  fun close() {
//    behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
  }

  fun release() {
//    behavior?.removeBottomSheetCallback(bottomSheetCallback)
  }
}