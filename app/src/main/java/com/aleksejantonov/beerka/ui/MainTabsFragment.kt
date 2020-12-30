package com.aleksejantonov.beerka.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.aleksejantonov.beerka.MainActivity
import com.aleksejantonov.beerka.R
import com.aleksejantonov.beerka.di.DI
import com.aleksejantonov.core.navigation.NavigationTab
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.show
import kotlinx.android.synthetic.main.fragment_main_tabs.*
import timber.log.Timber
import kotlin.math.abs

class MainTabsFragment : BaseFragment(R.layout.fragment_main_tabs) {
  private val viewModel by viewModels<MainTabsViewModel>()

  private val visibleContainer: View?
    get() = when {
      beerListContainer.isVisible -> beerListContainer
      favoriteBeersContainer.isVisible -> favoriteBeersContainer
      else -> null
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    toggleGroup.onSwitchTabClick { tab ->
      when (tab) {
        TabsSwitcherBlockView.SwitchTab.BEERS -> viewModel.onTabClick(NavigationTab.BEER_LIST, false)
        TabsSwitcherBlockView.SwitchTab.FAVORITES -> viewModel.onTabClick(NavigationTab.FAVORITES, false)
      }
    }
    toggleGroup.onFilterClick { showModalSheet() }

    beerListContainer.isVisible = true
    favoriteBeersContainer.isVisible = false

    (activity as? MainActivity)?.localRouter?.tabNavigation?.currentTab?.observe {
      toggleGroup.switchTab(TabsSwitcherBlockView.SwitchTab.values()[it.ordinal])
      visibleContainer?.show(false)
      when (it) {
        NavigationTab.BEER_LIST -> beerListContainer.show(true)
        NavigationTab.FAVORITES -> favoriteBeersContainer.show(true)
      }
    }

    setUpFloatingBottomBar()
  }

  override fun onNavigationBarHeight(navBarHeight: Int) {
    toggleGroup.setMargins(bottom = navBarHeight)
  }

  override fun onResume() {
    super.onResume()
    val tabNavigation = (activity as? MainActivity)?.localRouter?.tabNavigation ?: return
    if (tabNavigation.currentScreen() == null) {
      val fragment = DI.appComponent.globalFeatureProvider().provideFeatureBeerList()
      tabNavigation.switchTab({ fragment }, NavigationTab.BEER_LIST)
    }
  }

  private var oldEventY = 0f
  private var oldViewY = 0f

  @SuppressLint("ClickableViewAccessibility")
  private fun showModalSheet() {
    (view as? ViewGroup)?.addView(
      ConstraintLayout(requireContext()).apply {
        layoutParams = LayoutHelper.getFrameParams(
          context = context,
          width = LayoutHelper.MATCH_PARENT,
          height = 400,
          gravity = Gravity.BOTTOM
        )
        setBackgroundResource(R.color.appGrey)
        setOnTouchListener { view, event ->
          when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
              oldViewY = view.y
              oldEventY = event.y
              true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//              view.y = oldViewY
              view.animate().y(oldViewY).setDuration(300L).start()
              oldViewY = 0f
              oldEventY = 0f
              true
            }
            MotionEvent.ACTION_MOVE -> {
              val dy = event.y - oldEventY
              if (dy > 0) {
                Timber.e("MOVE DY: $dy")
                view.y = view.y + dy
              }
              true
            }
            else -> false
          }
        }
      }
    )
  }

  private fun setUpFloatingBottomBar() {
    val fullTranslation = dpToPx(tabNavigationBarHeight.toFloat()).toFloat()
    val showDuration = 200L
    val hideDuration = 300L
    val animations = mutableListOf<ObjectAnimator>()
    toggleGroup.translationY = fullTranslation
    bottomNavigationViewModel.showData.observe { show ->
      val destTranslation = if (show) 0f else fullTranslation
      val currentTranslation = toggleGroup.translationY
      if (currentTranslation != destTranslation) {
        val duration =
          if (show) currentTranslation / fullTranslation * showDuration
          else (1 - currentTranslation / fullTranslation) * hideDuration

        animations.forEach { it.cancel() }
        animations.clear()

        ObjectAnimator.ofFloat(toggleGroup, "translationY", currentTranslation, destTranslation).apply {
          this.duration = duration.toLong()
          this.interpolator = if (show) DecelerateInterpolator() else AccelerateInterpolator()
          animations.add(this)
          this.start()
        }
        ObjectAnimator.ofFloat(toggleGroup, "alpha", toggleGroup.alpha, if (show) 1f else 0f).apply {
          this.duration = duration.toLong()
          this.interpolator = if (show) AccelerateInterpolator() else DecelerateInterpolator()
          animations.add(this)
          this.start()
        }
      }
    }
  }

}