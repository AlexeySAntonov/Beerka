package com.aleksejantonov.core.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.ui.base.mvvm.navBarHeight
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.core.ui.base.mvvm.statusBarHeight
import timber.log.Timber

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
  private val statusBarHeight by lazy { statusBarHeight() }
  private val navBarHeight by lazy { navBarHeight() }

  private var stateSaved = false
  private val componentKey by lazy { arguments?.getLong(COMPONENT_KEY) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onStatusBarHeight(statusBarHeight)
    onNavigationBarHeight(navBarHeight)
    getViewToApplyStatusBarPadding(view).forEach { it.setPaddings(top = statusBarHeight) }
    getViewToApplyNavigationBarPadding(view).forEach { it.setPaddings(bottom = navBarHeight) }
    getViewToApplyStatusBarMargin(view).forEach { it.setMargins(top = statusBarHeight) }
    getViewToApplyNavigationBarMargin(view).forEach { it.setMargins(bottom = navBarHeight) }
  }

  override fun onStart() {
    super.onStart()
    stateSaved = false
  }

  override fun onResume() {
    super.onResume()
    stateSaved = false
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    stateSaved = true
  }

  override fun onDestroyView() {
    val viewGroup = (view as? ViewGroup)
    viewGroup?.let { releaseAdaptersRecursively(it) }
    super.onDestroyView()
  }

  override fun onDestroy() {
    super.onDestroy()

    //We leave the screen and respectively all fragments will be destroyed
    if (activity?.isFinishing == true) {
      componentKey?.let { ComponentsManager.release(it) }
      return
    }

    // When we rotate device isRemoving() return true for fragment placed in backstack
    // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
    if (stateSaved) {
      stateSaved = false
      return
    }

    // See https://github.com/Arello-Mobile/Moxy/issues/24
    var anyParentIsRemoving = false
    var parent = parentFragment
    while (!anyParentIsRemoving && parent != null) {
      anyParentIsRemoving = parent.isRemoving
      parent = parent.parentFragment
    }

    if (isRemoving || anyParentIsRemoving) {
      componentKey?.let { ComponentsManager.release(it) }
    }
  }

  private fun releaseAdaptersRecursively(viewGroup: ViewGroup) {
    for (i in 0 until (viewGroup.childCount)) {
      when (val child = viewGroup.getChildAt(i)) {
        is RecyclerView -> {
          (viewGroup.getChildAt(i) as? RecyclerView)?.adapter = null
          Timber.d("Fragment recycler adapter released: ${viewGroup.getChildAt(i)}")
        }
        is ViewGroup -> releaseAdaptersRecursively(child)
      }
    }
  }

  open fun onReturnToScreen() {}

  protected open fun getViewToApplyStatusBarPadding(root: View): Array<View> = emptyArray()
  protected open fun getViewToApplyNavigationBarPadding(root: View): Array<View> = emptyArray()

  protected open fun onStatusBarHeight(statusBarHeight: Int) {}
  protected open fun onNavigationBarHeight(navBarHeight: Int) {}

  protected open fun getViewToApplyStatusBarMargin(root: View): Array<View> = emptyArray()
  protected open fun getViewToApplyNavigationBarMargin(root: View): Array<View> = emptyArray()

  protected fun adjustResize() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
  }

  protected fun adjustPan() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
  }

  protected fun adjustNothing() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
  }

  protected inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(viewLifecycleOwner, { observer.invoke(it) })
  }

  companion object {
    const val COMPONENT_KEY = "COMPONENT_KEY"
    const val ARG_IS_IN_TAB_NAVIGATION = "arg_is_in_tab_navigation"
  }
}