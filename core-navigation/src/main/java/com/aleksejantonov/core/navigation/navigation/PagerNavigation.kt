package com.aleksejantonov.core.navigation.navigation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

class PagerNavigation(
    override val containerId: Int
) : Navigation {

  var activityRef: WeakReference<FragmentActivity>? = null

  private val pager get() = activityRef?.get()?.findViewById<View>(containerId) as? Pager

  fun openPage(index: Int) {
    pager?.current = index
  }

  fun resetToRoot() {
    openPage(0)
  }

  override fun back(force: Boolean): Boolean = pager?.current?.let {
    if (it == 0) false
    else {
      resetToRoot()
      true
    }
  } ?: false

  override fun currentScreen(): Fragment? {
    throw IllegalStateException("Not implemented for pager navigation")
  }

  fun isRoot() = pager?.current == 0

  interface Pager {
    var current: Int
    val isActive: Boolean
  }
}