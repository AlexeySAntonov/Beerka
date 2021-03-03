package com.aleksejantonov.core.navigation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.localrouting.Navigator

abstract class BaseNavHostActivity : AppCompatActivity() {

  protected val viewModel by viewModels<NavHostViewModel>()

  override fun onResume() {
    super.onResume()
    viewModel.attachNavigator(navigator())
  }

  override fun onPause() {
    viewModel.detachNavigator()
    super.onPause()
  }

  override fun onBackPressed() {
    navigator().handleBack(false)
  }

  fun currentScreen(): Fragment? = viewModel.currentScreen()

  protected abstract fun navigator(): Navigator
}