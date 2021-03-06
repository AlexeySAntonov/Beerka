package com.aleksejantonov.core.navigation

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.localrouting.Navigator
import com.aleksejantonov.core.ui.base.BottomSheetable
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.mvvm.addModalsContainer

abstract class BaseNavHostActivity : AppCompatActivity(), ModalsHost {

  protected val viewModel by viewModels<NavHostViewModel>()

  override val modalsContainer by lazy {
    FrameLayout(this).apply {
      layoutParams = LayoutHelper.getFrameParams(context, LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addModalsContainer(modalsContainer)
  }

  override fun onResume() {
    super.onResume()
    viewModel.attachNavigator(navigator())
  }

  override fun onPause() {
    removeModalView()
    viewModel.detachNavigator()
    super.onPause()
  }

  override fun onBackPressed() {
    if (modalsContainer.childCount > 0) {
      (modalsContainer[0] as? BottomSheetable)?.animateHide() ?: navigator().handleBack(false)
    } else {
      navigator().handleBack(false)
    }
  }

  override fun addModalView(view: View) {
    modalsContainer.addView(view)
  }

  override fun removeModalView() {
    modalsContainer.removeAllViews()
  }

  fun currentScreen(): Fragment? = viewModel.currentScreen()

  protected abstract fun navigator(): Navigator
}