package com.aleksejantonov.core.navigation

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.navigation.transition.ActivityTransition
import com.aleksejantonov.core.ui.base.BottomSheetable
import com.aleksejantonov.core.ui.base.mvvm.addViewOnTheVeryTop
import com.aleksejantonov.core.ui.base.mvvm.removeViewFromTheVeryTop
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class GlobalRouter @Inject constructor(
  private val globalFeatureProvider: GlobalFeatureProvider
) {

  /** FEATURE NAVIGATION REGION */

  fun openDetailsFeature(screenData: ScreenData) {
    val screenKey = globalFeatureProvider.provideFeatureDetails(screenData)
    openFullScreen(screenKey)
  }

  /** MODALS */

  private var modalViewRef: WeakReference<View>? = null

  private fun openModalView(view: View) {
    removeCurrentModal()
    modalViewRef = WeakReference(view)
    modalViewRef?.get()?.let { activityRef?.get().addViewOnTheVeryTop(it) }
    (modalViewRef?.get() as? BottomSheetable)?.animateShow()
  }

  fun removeCurrentModal() {
    modalViewRef?.get()?.let { activity()?.removeViewFromTheVeryTop(it) }
    modalViewRef = null
  }

  fun openFilterFeature(context: Context) {
    openModalView(globalFeatureProvider.provideFeatureFilter(context))
  }

  /** FEATURE NAVIGATION REGION END */

  private val activityArgs = mutableMapOf<String, Fragment>()

  private var activityRef: WeakReference<BaseNavHostActivity>? = null

  fun popActivityArgs(key: String): Fragment? =
    activityArgs[key]?.also {
      activityArgs.remove(key)
    }

  fun attach(activity: BaseNavHostActivity) {
    activityRef = WeakReference(activity)
  }

  fun detach(activity: BaseNavHostActivity) {
    removeCurrentModal()
    if (activity === activity()) {
      activityRef = null
    }
  }

  fun <T : FragmentActivity> openActivity(
    current: FragmentActivity,
    dest: KClass<T>,
    fragment: Fragment,
    transition: ActivityTransition = ActivityTransition.Slide()
  ) {
    val key = UUID.randomUUID().toString()
    activityArgs[key] = fragment
    current.startActivity(
      Intent(current, dest.java)
        .putExtra(EXTRA_FRAGMENT_KEY, key)
        .putExtra(EXTRA_ACTIVITY_TRANSITION, transition)
    )
    current.overridePendingTransition(transition.openEnter, transition.openExit)
  }

  fun switchTab(screenKey: String, tab: NavigationTab) {
    navigationRoutes.sendBlocking(NavigationRoute.Tab(screenKey, tab))
  }

  fun openFullScreen(screenKey: String, addToStack: Boolean = true) {
    navigationRoutes.sendBlocking(NavigationRoute.FullScreen(screenKey, addToStack))
  }

  fun back(force: Boolean = false) {
    (modalViewRef?.get() as? BottomSheetable)?.animateHide()
      ?: run {
        navigationRoutes.sendBlocking(NavigationRoute.Back(force))
      }
  }

  fun currentScreen(): Fragment? = activity()?.currentScreen()

  fun activity(): BaseNavHostActivity? = activityRef?.get()

  companion object {
    const val EXTRA_FRAGMENT_KEY = "extra_fragment_key"
    const val EXTRA_ACTIVITY_TRANSITION = "extra_activity_transition"

    private val navigationRoutes = BroadcastChannel<NavigationRoute>(1)
    fun observeRoutes() = navigationRoutes.asFlow()
  }
}