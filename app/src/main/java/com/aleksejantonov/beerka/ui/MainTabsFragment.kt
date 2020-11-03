package com.aleksejantonov.beerka.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.aleksejantonov.beerka.MainActivity
import com.aleksejantonov.beerka.R
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.show
import kotlinx.android.synthetic.main.fragment_main_tabs.*

class MainTabsFragment : BaseFragment(R.layout.fragment_main_tabs) {
    private val viewModel by viewModels<MainTabsViewModel>()

    private val visibleContainer: View?
        get() = when {
            beersListTab.isVisible -> beersListTab
            favoriteBeersTab.isVisible -> favoriteBeersTab
            else -> null
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beersListTab.setOnClickListener { viewModel.onTabClick(NavigationTab.BEER_LIST, false) }
        favoriteBeersTab.setOnClickListener { viewModel.onTabClick(NavigationTab.FAVORITES, false) }

        beerListContainer.isVisible = true
        favoriteBeersContainer.isVisible = false

        (activity as? MainActivity)?.localRouter?.tabNavigation?.currentTab?.observe {
            visibleContainer?.show(false)
            when (it) {
                NavigationTab.BEER_LIST -> beerListContainer.show(true)
                NavigationTab.FAVORITES -> favoriteBeersContainer.show(true)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val tabNavigation = (activity as? MainActivity)?.localRouter?.tabNavigation ?: return
        if (tabNavigation.currentScreen() == null) {
//            tabNavigation.switchTab({ /** BeerListFragment */ }, NavigationTab.BEER_LIST)
        }
    }

}