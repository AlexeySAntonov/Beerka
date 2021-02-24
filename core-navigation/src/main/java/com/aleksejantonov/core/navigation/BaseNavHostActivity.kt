package com.aleksejantonov.core.navigation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.localrouting.Navigator

abstract class BaseNavHostActivity : AppCompatActivity() {

  protected val viewModel by viewModels<NavHostViewModel>()

  override fun onResume() {
    super.onResume()
//    AppRouter.attach(this)
    viewModel.attachLocalRouter(localRouter())
  }

  override fun onPause() {
//    AppRouter.detach(this)
    viewModel.detachLocalRouter()
    super.onPause()
  }

  override fun onBackPressed() {
    localRouter().handleBack(false)
  }

  fun currentScreen(): Fragment? = viewModel.currentScreen()

  protected abstract fun localRouter(): Navigator
}