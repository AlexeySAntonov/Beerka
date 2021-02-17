package com.aleksejantonov.core.ui.base.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.R
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.google.android.material.slider.RangeSlider

class RangeSeekBarWithInfo(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

  private var label: TextView? = null
  private var rangeSlider: RangeSlider? = null

  init {
    layoutParams = LayoutHelper.getFrameParams(
      context = context,
      width = LayoutHelper.MATCH_PARENT,
      height = LayoutHelper.WRAP_CONTENT
    )
    clipChildren = false
    clipToPadding = false
    val contentPad = dpToPx(16f).toInt()
    setPaddings(left = contentPad, right = contentPad)
    setupLabel()
    setupSlider()
  }

  fun setLabel(text: String) {
    label?.text = text
  }

  fun setRangeValues(from: Float, to: Float) {
    rangeSlider?.apply {
      valueFrom = from
      valueTo = to
      setValues(from, to)
    }
  }

  private fun setupLabel() {
    label = TextView(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.WRAP_CONTENT,
        height = LayoutHelper.WRAP_CONTENT,
        gravity = Gravity.BOTTOM,
        bottomMargin = LABEL_BOTTOM_MARGIN
      )
      textSize = 15f
      typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }
    label?.let { addView(it) }
  }

  private fun setupSlider() {
    rangeSlider = RangeSlider(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.WRAP_CONTENT,
        gravity = Gravity.BOTTOM
      )
    }
    rangeSlider?.let { addView(it) }
  }

  companion object {
    private const val LABEL_BOTTOM_MARGIN = 56 // 8 + com.google.android.material.R.dimen.mtrl_slider_widget_height
  }
}