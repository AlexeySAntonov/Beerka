package com.aleksejantonov.core.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aleksejantonov.core.ui.base.bottomnavigation.BottomNavigationScrollHandlers
import com.aleksejantonov.core.ui.base.bottomnavigation.BottomNavigationViewModel
import com.aleksejantonov.core.ui.base.mvvm.navBarHeight
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.core.ui.base.mvvm.statusBarHeight
import kotlinx.coroutines.Job
import timber.log.Timber

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
  protected var uiStateJob: Job? = null

  private val statusBarHeight by lazy { statusBarHeight() }
  private val navBarHeight by lazy { navBarHeight() }

  protected val tabNavigationBarHeight by lazy { resources.getDimensionPixelOffset(R.dimen.tabs_switcher_height) }
  protected val bottomNavigationViewModel by activityViewModels<BottomNavigationViewModel>()

  private val recyclerViewScrollListener by lazy {
    BottomNavigationScrollHandlers.RecyclerViewListener(bottomNavigationViewModel)
  }
  private val nestedScrollViewScrollListener by lazy {
    BottomNavigationScrollHandlers.NestedScrollViewListener(bottomNavigationViewModel)
  }

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
    val scrollableView = getScrollableViewToChangeBottomNavigationState() ?: return
    when (scrollableView) {
      is RecyclerView -> scrollableView.addOnScrollListener(recyclerViewScrollListener)
      is NestedScrollView -> scrollableView.setOnScrollChangeListener(nestedScrollViewScrollListener)
    }
  }

  override fun onStop() {
    super.onStop()
    val scrollableView = getScrollableViewToChangeBottomNavigationState() ?: return
    when (scrollableView) {
      is RecyclerView -> scrollableView.removeOnScrollListener(recyclerViewScrollListener)
      is NestedScrollView -> scrollableView.setOnScrollChangeListener(null as NestedScrollView.OnScrollChangeListener?)
    }
  }

  override fun onDestroyView() {
    uiStateJob?.cancel()
    uiStateJob = null
    val viewGroup = (view as? ViewGroup)
    viewGroup?.let { releaseAdaptersRecursively(it) }
    super.onDestroyView()
  }

  private fun releaseAdaptersRecursively(viewGroup: ViewGroup) {
    for (i in 0 until (viewGroup.childCount)) {
      when (val child = viewGroup.getChildAt(i)) {
        is RecyclerView -> {
          (viewGroup.getChildAt(i) as? RecyclerView)?.adapter = null
          Timber.d("Fragment recycler adapter released: ${viewGroup.getChildAt(i)}")
        }
        is ViewPager2 -> {
          (viewGroup.getChildAt(i) as? ViewPager2)?.adapter = null
          Timber.d("Fragment ViewPager2 adapter released: ${viewGroup.getChildAt(i)}")
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

  protected open fun getScrollableViewToChangeBottomNavigationState(): View? = null

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
    const val SCREEN_DATA = "SCREEN_DATA"
    const val ARG_IS_IN_TAB_NAVIGATION = "arg_is_in_tab_navigation"
  }
}