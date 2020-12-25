package com.aleksejantonov.core.navigation

import android.content.Intent
import android.os.Bundle
import com.aleksejantonov.core.navigation.localrouting.OverlayLocalRouter
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.localrouting.LocalRouter
import com.aleksejantonov.core.navigation.transition.ActivityTransition

class NavHostActivity : BaseNavHostActivity() {

  private val transition: ActivityTransition by lazy {
    (intent.getSerializableExtra(AppRouter.EXTRA_ACTIVITY_TRANSITION) as? ActivityTransition) ?: ActivityTransition.Slide()
  }

  private val localRouter by lazy {
//    val bottomSheetHost = supportFragmentManager
//        .findFragmentById(R.id.persistentBottomSheetContainer)
//        ?: throw IllegalStateException("Bottom sheet host not found")
    OverlayLocalRouter(
        activity = this,
        fragmentNavigation = FragmentNavigation(R.id.fragmentContainerView, supportFragmentManager/**, SlideTransitionProvider()*/),
        persistentBottomNavigation = PersistentBottomSheetNavigation(R.id.navContainer/**, bottomSheetHost*/)
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nav_host)

    if (savedInstanceState == null) {
      val key = intent.getStringExtra(AppRouter.EXTRA_FRAGMENT_KEY) ?: return
      val fragment = AppRouter.popActivityArgs(key)
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

  override fun localRouter(): LocalRouter = localRouter

  private fun handleIntent(intent: Intent) {
    // Under dev
  }
}