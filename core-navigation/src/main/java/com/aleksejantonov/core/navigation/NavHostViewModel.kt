package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.navigation.localrouting.EmptyNavigator
import com.aleksejantonov.core.navigation.localrouting.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavHostViewModel : BaseViewModel() {

  private var navigator: Navigator = EmptyNavigator

  private var initialFragment: Fragment? = null

  init {
    viewModelScope.launch(Dispatchers.Default) {
      GlobalRouter.observeRoutes().collect {
        withContext(Dispatchers.Main) { navigator.applyRoute(it) }
      }
    }
  }

  fun setInitialFragment(fragment: Fragment) {
    if (navigator is EmptyNavigator) {
      this.initialFragment = fragment
    } else {
      navigator.setInitialScreen(fragment)
    }
  }

  fun attachLocalRouter(navigator: Navigator) {
    this.navigator = navigator
    initialFragment?.let {
      navigator.setInitialScreen(it)
      initialFragment = null
    }
  }

  fun detachLocalRouter() {
    this.navigator = EmptyNavigator
  }

  fun currentScreen(): Fragment? = navigator.currentScreen()

  override fun onCleared() {
    navigator.onRelease()
    super.onCleared()
  }
}