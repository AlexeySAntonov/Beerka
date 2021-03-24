package com.aleksejantonov.core.navigation

import android.content.Context
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.module.injector.ScreenCustomDependencies
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRouter @Inject constructor(
  private val globalFeatureProvider: GlobalFeatureProvider,
  private val modalsRouter: ModalsRouter
) {

  /** FEATURE NAVIGATION REGION */

  fun openDetailsFeature(customDependencies: ScreenCustomDependencies) {
    val screenKey = globalFeatureProvider.provideFeatureDetails(customDependencies)
    openFullScreen(screenKey)
  }

  fun openFilterModal(context: Context) {
    modalsRouter.openFilterModal(context)
  }

  /** FEATURE NAVIGATION REGION END */

  private var activityRef: WeakReference<BaseNavHostActivity>? = null

  fun switchTab(screenKey: String, tab: NavigationTab) {
    navigationRoutes.sendBlocking(NavigationRoute.Tab(screenKey, tab))
  }

  fun openFullScreen(screenKey: String, addToStack: Boolean = true) {
    navigationRoutes.sendBlocking(NavigationRoute.FullScreen(screenKey, addToStack))
  }

  fun back(force: Boolean = false) {
    if (modalsRouter.handleBack()) return
    navigationRoutes.sendBlocking(NavigationRoute.Back(force))
  }

  fun activity(): BaseNavHostActivity? = activityRef?.get()

  companion object {
    const val EXTRA_FRAGMENT_KEY = "extra_fragment_key"
    const val EXTRA_ACTIVITY_TRANSITION = "extra_activity_transition"

    private val navigationRoutes = BroadcastChannel<NavigationRoute>(1)
    fun observeRoutes() = navigationRoutes.asFlow()
  }
}