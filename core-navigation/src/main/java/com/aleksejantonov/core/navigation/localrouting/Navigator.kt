package com.aleksejantonov.core.navigation.localrouting

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.transition.ActivityTransition
import kotlin.reflect.KClass

interface Navigator {

  fun <T : FragmentActivity> openActivity(
    current: FragmentActivity,
    dest: KClass<T>,
    screenKey: String,
    transition: ActivityTransition = ActivityTransition.Slide()
  ) {
    current.startActivity(
      Intent(current, dest.java)
        .putExtra(GlobalRouter.EXTRA_FRAGMENT_KEY, screenKey)
        .putExtra(GlobalRouter.EXTRA_ACTIVITY_TRANSITION, transition)
    )
  }

  fun applyRoute(route: NavigationRoute)

  fun setInitialScreen(fragment: Fragment) {}

  fun currentScreen(): Fragment?

  fun onRelease() {}

  fun handleBack(force: Boolean)

}