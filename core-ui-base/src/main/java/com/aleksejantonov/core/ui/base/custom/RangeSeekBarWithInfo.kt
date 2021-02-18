package com.aleksejantonov.core.ui.base.custom

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.R
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.core.ui.base.mvvm.textColor
import com.aleksejantonov.core.ui.base.show
import com.google.android.material.slider.RangeSlider

class RangeSeekBarWithInfo(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

  private var title: TextView? = null
  private var fromField: TextView? = null
  private var toField: TextView? = null
  private var rangeSlider: RangeSlider? = null

  private var sliderStartTrackingTouchListener: (() -> Unit)? = null
  private var sliderStopTrackingTouchListener: (() -> Unit)? = null
  private var sliderChangeValueListener: ((Float) -> Unit)? = null

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
    setupTitle()
    setupAuxiliaryFields()
    setupSlider()
  }

  fun setTitle(text: String) {
    title?.text = text
  }

  fun setTitleTextColor(@ColorRes color: Int) {
    title?.textColor(color)
  }

  fun setTitleBackgroundRes(@DrawableRes background: Int) {
    title?.setBackgroundResource(background)
  }

  fun setTitleBackground(drawable: Drawable) {
    title?.background = drawable
  }

  fun setRangeValues(from: Float, to: Float) {
    rangeSlider?.apply {
      valueFrom = from
      valueTo = to
      setValues(from, to)
    }
    setAuxiliaryFieldsValues()
  }

  fun setRangeLabelBehaviour(behaviour: Int) {
    rangeSlider?.labelBehavior = behaviour
  }

  fun getRangeValues(): List<Float> {
    return rangeSlider?.values ?: emptyList()
  }

  fun setAuxiliaryFieldsValues() {
    getRangeValues().let { rangeValues ->
      rangeValues.firstOrNull()?.let { fromField?.text = "%.2f".format(it) }
      rangeValues.lastOrNull()?.let { toField?.text = "%.2f".format(it) }
    }
  }

  fun setStartTrackingListener(listener: () -> Unit) {
    sliderStartTrackingTouchListener = listener
  }

  fun setStopTrackingListener(listener: () -> Unit) {
    sliderStopTrackingTouchListener = listener
  }

  fun setChangeValueListener(listener: (Float) -> Unit) {
    sliderChangeValueListener = listener
  }

  fun toggleAuxiliaryFieldsVisibility(visible: Boolean) {
    fromField?.show(visible, AUXILIARY_FIELDS_APPEARANCE_DURATION)
    toField?.show(visible, AUXILIARY_FIELDS_APPEARANCE_DURATION)
  }

  private fun setupTitle() {
    title = TextView(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = 32,
        gravity = Gravity.BOTTOM,
        bottomMargin = LABEL_BOTTOM_MARGIN
      )
      textSize = 15f
      typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
      gravity = Gravity.CENTER
    }
    title?.let { addView(it) }
  }

  private fun setupAuxiliaryFields() {
    val sidePad = dpToPx(8f).toInt()
    fromField = TextView(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.WRAP_CONTENT,
        height = 32,
        gravity = Gravity.START or Gravity.BOTTOM,
        bottomMargin = LABEL_BOTTOM_MARGIN
      )
      setBackgroundResource(R.drawable.bg_auxiliary_field)
      setPaddings(left = sidePad, right = sidePad)
      textColor(R.color.white)
      textSize = 15f
      typeface = Typeface.create("sans-serif", Typeface.NORMAL)
      gravity = Gravity.CENTER
      isVisible = false
    }
    fromField?.let { addView(it) }

    toField = TextView(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.WRAP_CONTENT,
        height = 32,
        gravity = Gravity.END or Gravity.BOTTOM,
        bottomMargin = LABEL_BOTTOM_MARGIN
      )
      setBackgroundResource(R.drawable.bg_auxiliary_field)
      setPaddings(left = sidePad, right = sidePad)
      textColor(R.color.white)
      textSize = 15f
      typeface = Typeface.create("sans-serif", Typeface.NORMAL)
      gravity = Gravity.CENTER
      isVisible = false
    }
    toField?.let { addView(it) }
  }

  private fun setupSlider() {
    rangeSlider = RangeSlider(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.WRAP_CONTENT,
        gravity = Gravity.BOTTOM
      )
      addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: RangeSlider) {
          sliderStartTrackingTouchListener?.invoke()
        }

        override fun onStopTrackingTouch(slider: RangeSlider) {
          sliderStopTrackingTouchListener?.invoke()
        }
      })
      addOnChangeListener { slider, value, fromUser ->
        sliderChangeValueListener?.invoke(value)
      }
    }
    rangeSlider?.let { addView(it) }
  }

  companion object {
    private const val LABEL_BOTTOM_MARGIN = 56 // 8 + com.google.android.material.R.dimen.mtrl_slider_widget_height
    private const val AUXILIARY_FIELDS_APPEARANCE_DURATION = 330L
  }
}