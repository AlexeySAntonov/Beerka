package com.aleksejantonov.core.navigation

import android.content.Intent
import android.os.Bundle
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.localrouting.OverlayNavigator
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.localrouting.Navigator
import com.aleksejantonov.core.navigation.transition.ActivityTransition

class NavHostActivity : BaseNavHostActivity() {

  private val transition: ActivityTransition by lazy {
    (intent.getSerializableExtra(GlobalRouter.EXTRA_ACTIVITY_TRANSITION) as? ActivityTransition) ?: ActivityTransition.Slide()
  }

  private val localRouter by lazy {
//    val bottomSheetHost = supportFragmentManager
//        .findFragmentById(R.id.persistentBottomSheetContainer)
//        ?: throw IllegalStateException("Bottom sheet host not found")
    OverlayNavigator(
        activity = this,
        fragmentNavigation = FragmentNavigation(R.id.fragmentContainerView, supportFragmentManager/**, SlideTransitionProvider()*/),
        persistentBottomNavigation = PersistentBottomSheetNavigation(R.id.navContainer/**, bottomSheetHost*/)
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    overridePendingTransition(transition.openEnter, transition.openExit)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nav_host)

    if (savedInstanceState == null) {
      val key = intent.getStringExtra(GlobalRouter.EXTRA_FRAGMENT_KEY) ?: return
      val fragment = GlobalFeatureProvider.pop(key)
      if (fragment == null) {
        finish()
      } else {
        viewModel.setInitialFragment(fragment)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.firstStartEvent.observe(this, { handleIntent(intent) })
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(transition.closeEnter, transition.closeExit)
  }

  override fun localRouter(): Navigator = localRouter

  private fun handleIntent(intent: Intent) {
    // Under dev
  }
}