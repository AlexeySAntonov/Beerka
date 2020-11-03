package com.aleksejantonov.core.navigation

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import java.lang.ref.WeakReference
import java.util.*
import kotlin.reflect.KClass

object AppRouter {

    const val EXTRA_FRAGMENT_KEY = "extra_fragment_key"
    private val activityArgs = mutableMapOf<String, Fragment>()

    private var activityRef: WeakReference<BaseNavHostActivity>? = null

    private val navigationRoutes = BroadcastChannel<NavigationRoute>(1)

    fun observeRoutes() = navigationRoutes.asFlow()

    fun popActivityArgs(key: String): Fragment? =
        activityArgs[key]?.also {
            activityArgs.remove(key)
        }

    fun attach(activity: BaseNavHostActivity) {
        activityRef = WeakReference(activity)
    }

    fun detach(activity: BaseNavHostActivity) {
        if (activity === activity()) {
            activityRef = null
        }
    }

    fun <T : FragmentActivity> openActivity(current: FragmentActivity, dest: KClass<T>, fragment: Fragment) {
        val key = UUID.randomUUID().toString()
        activityArgs[key] = fragment
        current.startActivity(
            Intent(current, dest.java)
                .putExtra(EXTRA_FRAGMENT_KEY, key)
        )
    }

    fun switchTab(rootFactory: () -> Fragment, tab: NavigationTab) {
        navigationRoutes.sendBlocking(NavigationRoute.Tab(tab, rootFactory))
    }

    fun openFullScreen(fragment: Fragment, addToStack: Boolean = true) {
        navigationRoutes.sendBlocking(NavigationRoute.FullScreen({ fragment }, addToStack))
    }

    fun currentScreen(): Fragment? = activity()?.currentScreen()

    fun activity(): BaseNavHostActivity? = activityRef?.get()
}