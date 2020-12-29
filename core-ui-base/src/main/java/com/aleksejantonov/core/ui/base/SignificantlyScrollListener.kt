package com.aleksejantonov.core.ui.base

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * Just not to call `onScrolled` callback very often for performance reasons.
 */
abstract class SignificantlyScrollListener(private val numColumns: Int = 1) : RecyclerView.OnScrollListener() {
  private var milestone = 0
  private var scrollY = 0
  private var step = 0

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    if (step == 0) {
      step = calculateStep(numColumns, recyclerView.resources)
    }
    if (scrollY == 0) {
      scrollY = recyclerView.computeVerticalScrollOffset()
    } else {
      scrollY += dy
      val m = scrollY / step
      if (m != milestone) {
        milestone = m
        onSignificantlyScrolled(recyclerView, dx, dy)
      }
    }
  }

  protected abstract fun onSignificantlyScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)

  companion object {
    private const val F = 0.16722408026755853f
    fun calculateStep(numColumns: Int, resources: Resources): Int {
      return (resources.displayMetrics.heightPixels * F).roundToInt() / numColumns
    }
  }
}