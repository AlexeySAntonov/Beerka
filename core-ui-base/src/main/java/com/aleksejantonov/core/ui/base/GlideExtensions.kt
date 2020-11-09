package com.aleksejantonov.core.ui.base

import android.view.View
import com.bumptech.glide.load.engine.GlideException

fun releaseGlide(vararg views: View) {
  try {
    for (view in views) {
      GlideApp.with(view).clear(view)
    }
  } catch (ignored: IllegalArgumentException) {
  }
}

fun originException(exception: GlideException?): Throwable? {
  val e = exception?.causes?.firstOrNull()
  return if (e !is GlideException) e
  else originException(e)
}