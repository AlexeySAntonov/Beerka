package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.ui.base.BaseViewModel
import hellomobile.hello.core.ui.navigation.EmptyLocalRouter
import hellomobile.hello.core.ui.navigation.LocalRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavHostViewModel : BaseViewModel() {

  private var localRouter: LocalRouter = EmptyLocalRouter

  private var initialFragment: Fragment? = null

  init {
    viewModelScope.launch(Dispatchers.Default) {
      AppRouter.observeRoutes().collect {
        withContext(Dispatchers.Main) { localRouter.applyRoute(it) }
      }
    }
  }

  fun setInitialFragment(fragment: Fragment) {
    if (localRouter is EmptyLocalRouter) {
      this.initialFragment = fragment
    } else {
      localRouter.applyRoute(NavigationRoute.FullScreen({ fragment }, true))
    }
  }

  fun attachLocalRouter(localRouter: LocalRouter) {
    this.localRouter = localRouter
    initialFragment?.let {
      localRouter.applyRoute(NavigationRoute.FullScreen({ it }, true))
      initialFragment = null
    }
  }

  fun detachLocalRouter() {
    this.localRouter = EmptyLocalRouter
  }

  fun currentScreen(): Fragment? = localRouter.currentScreen()

  override fun onCleared() {
    localRouter.onRelease()
    super.onCleared()
  }
}