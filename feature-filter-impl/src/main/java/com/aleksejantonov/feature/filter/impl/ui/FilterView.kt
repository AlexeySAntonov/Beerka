package com.aleksejantonov.feature.filter.impl.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.resources.beerColor
import com.aleksejantonov.core.ui.base.BottomSheetable
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.custom.RangeSeekBarWithInfo
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.core.ui.base.mvvm.navBarHeight
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.feature.filter.impl.R
import com.google.android.material.slider.LabelFormatter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

class FilterView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), BottomSheetable {

  private var componentKey: String by Delegates.notNull()
  private val component: ViewModelFactoryProvider by lazy {
    requireNotNull(ComponentsManager.get(componentKey) as? ViewModelFactoryProvider)
  }
  private val viewModel: FilterViewModel by lazy { component.viewModelFactory().create(FilterViewModel::class.java) }
  private var scope: CoroutineScope? = null

  private var dimView: View? = null
  private var bottomSheetContainer: LinearLayout? = null
  private var abvSeekBar: RangeSeekBarWithInfo? = null
  private var ibuSeekBar: RangeSeekBarWithInfo? = null
  private var ebcSeekBar: RangeSeekBarWithInfo? = null

  init {
    layoutParams = LayoutHelper.getFrameParams(
      context = context,
      width = LayoutHelper.MATCH_PARENT,
      height = LayoutHelper.MATCH_PARENT,
      gravity = Gravity.BOTTOM
    )
    clipChildren = false
    clipToPadding = false

    setupDim()
    setupBottomSheetContainer()

    scope = CoroutineScope(Dispatchers.Main)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    scope?.launch {
      viewModel.data.collect { filter ->
        abvSeekBar?.setValues(filter.abvPair.first, filter.abvPair.second)
        ibuSeekBar?.setValues(filter.ibuPair.first, filter.ibuPair.second)
        ebcSeekBar?.setValues(filter.ebcPair.first, filter.ebcPair.second)
      }
    }
  }

  override fun onDetachedFromWindow() {
    scope?.cancel()
    scope = null
    ComponentsManager.release(componentKey)
    super.onDetachedFromWindow()
  }

  override fun animateShow() {
    AnimatorSet().apply {
      playTogether(
        ObjectAnimator.ofFloat(requireNotNull(dimView), View.ALPHA, 0f, 1f)
          .setDuration(SHEET_APPEARANCE_DURATION),
        ObjectAnimator.ofFloat(requireNotNull(bottomSheetContainer), View.TRANSLATION_Y, dpToPx(BOTTOM_SHEET_HEIGHT), dpToPx(-16f))
          .setDuration(SHEET_APPEARANCE_DURATION),
        ObjectAnimator.ofFloat(requireNotNull(bottomSheetContainer), View.TRANSLATION_Y, dpToPx(-16f), 0f)
          .setDuration(SHEET_BOUNCING_DURATION)
          .apply { startDelay = SHEET_APPEARANCE_DURATION },
        ObjectAnimator.ofFloat(requireNotNull(abvSeekBar), View.TRANSLATION_Y, dpToPx(BOTTOM_SHEET_HEIGHT), 0f)
          .setDuration(SEEK_BAR_APPEARANCE_DURATION)
          .apply { startDelay = SHEET_APPEARANCE_DURATION + SHEET_BOUNCING_DURATION },
        ObjectAnimator.ofFloat(requireNotNull(ibuSeekBar), View.TRANSLATION_Y, dpToPx(BOTTOM_SHEET_HEIGHT), 0f)
          .setDuration(SEEK_BAR_APPEARANCE_DURATION)
          .apply { startDelay = SHEET_APPEARANCE_DURATION + SHEET_BOUNCING_DURATION + 75L },
        ObjectAnimator.ofFloat(requireNotNull(ebcSeekBar), View.TRANSLATION_Y, dpToPx(BOTTOM_SHEET_HEIGHT), 0f)
          .setDuration(SEEK_BAR_APPEARANCE_DURATION)
          .apply { startDelay = SHEET_APPEARANCE_DURATION + SHEET_BOUNCING_DURATION + 150L },
      )
      interpolator = AccelerateDecelerateInterpolator()
      start()
    }
  }

  override fun animateHide() {
    AnimatorSet().apply {
      playTogether(
        ObjectAnimator.ofFloat(requireNotNull(dimView), View.ALPHA, 1f, 0f),
        ObjectAnimator.ofFloat(requireNotNull(bottomSheetContainer), View.TRANSLATION_Y, 0f, dpToPx(BOTTOM_SHEET_HEIGHT)),
      )
      interpolator = AccelerateInterpolator()
      duration = 130L
      doOnEnd { AppRouter.removeCurrentModal() }
      start()
    }
  }

  private fun setupDim() {
    dimView = View(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.MATCH_PARENT
      )
      alpha = 0.0f
      setBackgroundResource(R.color.semiTransparentDark)
      setOnClickListener { animateHide() }
    }
    dimView?.let { addView(it) }
  }

  private fun setupBottomSheetContainer() {
    bottomSheetContainer = LinearLayout(context).apply {
      layoutParams = LayoutHelper.getFrameParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = BOTTOM_SHEET_HEIGHT.toInt(),
        gravity = Gravity.BOTTOM
      )
      orientation = LinearLayout.VERTICAL
      setBackgroundResource(R.drawable.bg_rounded_20dp)
      translationY = dpToPx(BOTTOM_SHEET_HEIGHT)
      setPaddings(bottom = context.navBarHeight() + dpToPx(24f).toInt())

      addView(setupAbvSeekBar())
      addView(setupIbuSeekBar())
      addView(setupEbcSeekBar())
    }
    bottomSheetContainer?.let { addView(it) }
  }

  private fun setupAbvSeekBar(): View {
    abvSeekBar = RangeSeekBarWithInfo(context).apply {
      layoutParams = LayoutHelper.getLinearParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.MATCH_PARENT,
        topMargin = 16
      )
      setTitle(resources.getString(R.string.alcohol_by_volume))
      setRange(0f, 60f)
      setRangeLabelBehaviour(LabelFormatter.LABEL_FLOATING)
      setStartTrackingListener { toggleAuxiliaryFieldsVisibility(false) }
      setStopTrackingListener { toggleAuxiliaryFieldsVisibility(true) }
      setChangeValueListener { setAuxiliaryFieldsValues() }
      toggleAuxiliaryFieldsVisibility(true)
      translationY = dpToPx(BOTTOM_SHEET_HEIGHT)
    }
    return requireNotNull(abvSeekBar)
  }

  private fun setupIbuSeekBar(): View {
    ibuSeekBar = RangeSeekBarWithInfo(context).apply {
      layoutParams = LayoutHelper.getLinearParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.MATCH_PARENT,
        topMargin = 8
      )
      setTitle(resources.getString(R.string.bitterness_units))
      setRange(0f, 200f)
      setRangeLabelBehaviour(LabelFormatter.LABEL_FLOATING)
      setStartTrackingListener { toggleAuxiliaryFieldsVisibility(false) }
      setStopTrackingListener { toggleAuxiliaryFieldsVisibility(true) }
      setChangeValueListener { setAuxiliaryFieldsValues() }
      toggleAuxiliaryFieldsVisibility(true)
      translationY = dpToPx(BOTTOM_SHEET_HEIGHT)
    }
    return requireNotNull(ibuSeekBar)
  }

  private fun setupEbcSeekBar(): View {
    ebcSeekBar = RangeSeekBarWithInfo(context).apply {
      layoutParams = LayoutHelper.getLinearParams(
        context = context,
        width = LayoutHelper.MATCH_PARENT,
        height = LayoutHelper.MATCH_PARENT,
        topMargin = 8
      )
      setTitle(resources.getString(R.string.beer_and_wort_colour))
      setTitleTextColor(R.color.white)
      setTitleBackgroundRes(R.drawable.bg_rounded_20dp)
      setRange(0f, 80f)
      setRangeLabelBehaviour(LabelFormatter.LABEL_GONE)
      setChangeValueListener { updateEbcLabelBackground(getValues()) }
      toggleAuxiliaryFieldsVisibility(false)
      translationY = dpToPx(BOTTOM_SHEET_HEIGHT)
    }
    ebcSeekBar?.let { updateEbcLabelBackground(it.getValues()) }
    return requireNotNull(ebcSeekBar)
  }

  private fun updateEbcLabelBackground(values: List<Float>) {
    ebcSeekBar?.setTitleBackground(
      GradientDrawable().apply {
        cornerRadius = dpToPx(8f)
        gradientType = GradientDrawable.LINEAR_GRADIENT
        orientation = GradientDrawable.Orientation.LEFT_RIGHT
        colors = values.map { beerColor(it) }.toIntArray()
      }
    )
  }

  private var oldEventY = 0f
  private var oldViewY = 0f

  @SuppressLint("ClickableViewAccessibility")
  private fun setupBottomSheetTouch() {
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
            view.y = view.y + dy
          }
          true
        }
        else -> false
      }
    }
  }

  companion object {
    private const val BOTTOM_SHEET_HEIGHT = 400f
    private const val SHEET_APPEARANCE_DURATION = 220L
    private const val SHEET_BOUNCING_DURATION = 160L
    private const val SEEK_BAR_APPEARANCE_DURATION = 120L

    fun create(context: Context, componentKey: String): FilterView = FilterView(context).apply {
      this.componentKey = componentKey
    }
  }
}