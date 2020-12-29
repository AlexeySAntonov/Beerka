package com.aleksejantonov.beerka.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.aleksejantonov.beerka.R
import com.aleksejantonov.core.ui.base.mvvm.*
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.android.synthetic.main.view_tabs_switcher_block.view.*

class TabsSwitcherBlockView(context: Context, attrs: AttributeSet? = null) : MaterialButtonToggleGroup(context, attrs) {

  enum class SwitchTab {
    BEERS,
    FAVORITES
  }

  init {
    inflate(R.layout.view_tabs_switcher_block, attachToRoot = true)

    orientation = HORIZONTAL
    gravity = Gravity.CENTER
    isSingleSelection = true

    val horPad = context.dpToPx(HORIZONTAL_PADDING)
    val verPad = context.dpToPx(VERTICAL_PADDING)
    setPaddings(left = horPad, top = verPad, right = horPad, bottom = verPad)
    clipToPadding = false

    setBackgroundResource(android.R.color.transparent)

    initViews()
  }

  private var listener: ((SwitchTab) -> Unit)? = null

  fun onSwitchTabClick(listener: (SwitchTab) -> Unit) {
    this.listener = listener
  }

  fun switchTab(tab: SwitchTab) {
    when (tab) {
      SwitchTab.BEERS -> {
        beersToggle.textColor(R.color.white)
        beersToggle.setBackgroundTint(R.color.appBlue)
        favoritesToggle.textColor(R.color.appBlue)
        favoritesToggle.setBackgroundTint(R.color.white)
      }
      SwitchTab.FAVORITES -> {
        beersToggle.textColor(R.color.appBlue)
        beersToggle.setBackgroundTint(R.color.white)
        favoritesToggle.textColor(R.color.white)
        favoritesToggle.setBackgroundTint(R.color.appBlue)
      }
    }
  }

  private fun initViews() {
    beersToggle.textColor(R.color.white)
    beersToggle.setBackgroundTint(R.color.appBlue)
    beersToggle.setOnClickListener { _ ->
      listener?.invoke(SwitchTab.BEERS)
    }
    favoritesToggle.setOnClickListener { _ ->
      listener?.invoke(SwitchTab.FAVORITES)
    }
  }

  companion object {
    private const val HORIZONTAL_PADDING = 16f
    private const val VERTICAL_PADDING = 16f
  }
}