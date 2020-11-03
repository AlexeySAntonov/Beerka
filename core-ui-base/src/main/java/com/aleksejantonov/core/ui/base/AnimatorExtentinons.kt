package com.aleksejantonov.core.ui.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.TextView
import androidx.core.view.isVisible


fun View.fadeIn(duration: Long = 200L): ViewPropertyAnimator = animate().alpha(1f).setDuration(duration)


fun View.fadeOut(duration: Long = 200L): ViewPropertyAnimator = animate().alpha(0f).setDuration(duration)


fun View.show(show: Boolean, duration: Long = 200L) {
  isVisible = true
  val animator = if (show) fadeIn(duration) else fadeOut(duration)
  animator.setListener(object : AnimatorListenerAdapter() {
    override fun onAnimationEnd(animation: Animator?) {
      isVisible = show
    }
  })

}

fun TextView.textColorAnimation(colorFrom: Int, colorTo: Int, duration: Long = 200L) {
  val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
  colorAnimation.duration = duration
  colorAnimation.addUpdateListener { animator -> this.setTextColor(animator.animatedValue as Int) }
  colorAnimation.start()
}