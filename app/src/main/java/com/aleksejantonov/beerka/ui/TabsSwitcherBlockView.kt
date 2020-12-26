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

        setPaddings(left = PADDING * 2, top = PADDING, right = PADDING * 2, bottom = PADDING)
        clipToPadding = false

        setBackgroundResource(android.R.color.transparent)

        initViews()
    }

    private var lastCheckedType = SwitchTab.BEERS
    private var listener: ((SwitchTab) -> Unit)? = null

    fun onSwitchTabClick(listener: (SwitchTab) -> Unit) {
        this.listener = listener
    }

    private fun initViews() {
        beersToggle.textColor(R.color.white)
        beersToggle.setBackgroundTint(R.color.appGrey)
        beersToggle.setOnClickListener { _ ->
            if (lastCheckedType != SwitchTab.BEERS) {
                beersToggle.textColor(R.color.white)
                beersToggle.setBackgroundTint(R.color.appGrey)

                favoritesToggle.textColor(R.color.appGrey)
                favoritesToggle.setBackgroundTint(R.color.white)

                lastCheckedType = SwitchTab.BEERS
                listener?.invoke(lastCheckedType)
            }
        }
        favoritesToggle.setOnClickListener { _ ->
            if (lastCheckedType != SwitchTab.FAVORITES) {
                beersToggle.textColor(R.color.appGrey)
                beersToggle.setBackgroundTint(R.color.white)

                favoritesToggle.textColor(R.color.white)
                favoritesToggle.setBackgroundTint(R.color.appGrey)

                lastCheckedType = SwitchTab.FAVORITES
                listener?.invoke(lastCheckedType)
            }
        }
    }

    companion object {
        private const val PADDING = 16
    }
}