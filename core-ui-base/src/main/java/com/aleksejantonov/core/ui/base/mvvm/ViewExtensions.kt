package com.aleksejantonov.core.ui.base.mvvm

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.*
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment

val View.topMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).topMargin
val View.bottomMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
val View.leftMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
val View.rightMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).rightMargin

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
  val marginLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
  marginLayoutParams.setMargins(
      left ?: marginLayoutParams.leftMargin,
      top ?: marginLayoutParams.topMargin,
      right ?: marginLayoutParams.rightMargin,
      bottom ?: marginLayoutParams.bottomMargin
  )
  this.layoutParams = marginLayoutParams
}

fun View.setPaddings(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
  setPadding(
      left ?: paddingLeft,
      top ?: paddingTop,
      right ?: paddingRight,
      bottom ?: paddingBottom
  )
}

fun Context.dpToPx(dp: Float): Int {
  val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}

fun Fragment.dpToPx(dp: Float): Int {
  val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}

fun View.dpToPx(dp: Float): Float {
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
}

fun Fragment.statusBarHeight(): Int {
  return activity?.statusBarHeight() ?: 0
}

fun Activity.statusBarHeight(): Int {
  val rectangle = Rect()
  val window = window
  window.decorView.getWindowVisibleDisplayFrame(rectangle)
  return if (rectangle.top > 0) rectangle.top else statusBarHeightFromResources()
}

fun Activity.statusBarHeightFromResources(): Int {
  val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
  if (resourceId > 0) {
    return resources.getDimensionPixelSize(resourceId)
  }
  return 0
}

fun Context.navBarHeight(isLandscapeMode: Boolean = false): Int {
  if (hasSoftBottomBar(isLandscapeMode)) {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
      return resources.getDimensionPixelSize(resourceId)
    }
  }
  return 0
}

fun Fragment.navBarHeight(): Int = requireContext().navBarHeight()

fun Context.hasSoftBottomBar(isLandscapeMode: Boolean = false): Boolean {
  val bottomBarHeight = dpToPx(48f) // 48 is bottom bar height on Nexus 6 at least
  val screenSize = screenSize(this)
  val fullSize = fullScreenSize(this)
  return if (!isLandscapeMode) fullSize.y - screenSize.y >= bottomBarHeight
  else fullSize.x - screenSize.x >= bottomBarHeight
}

fun screenSize(context: Context): Point {
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val size = Point()
  display.getSize(size)
  return size
}

fun fullScreenSize(context: Context): Point {
  // fix for cases when bottom of the screen has soft action bar that 'steals' tiny amount of parent height
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val size = Point()
  display.getRealSize(size)
  return size
}

fun TextView.textColor(id: Int) {
  setTextColor(ContextCompat.getColor(context, id))
}

fun View.setBackgroundTint(@ColorRes id: Int) {
  val wrappedDrawable = DrawableCompat.wrap(background)
  DrawableCompat.setTint(wrappedDrawable.mutate(), ContextCompat.getColor(context, id))
}

fun Context.getScreenHeight(): Int {
  val size = Point()
  this.unwrap().windowManager.defaultDisplay.getSize(size)
  return size.y
}

fun Context.getScreenWidth(): Int {
  val size = Point()
  this.unwrap().windowManager.defaultDisplay.getSize(size)
  return size.x
}

fun Context.unwrap(): Activity {
  var unwrappedContext = this
  while (unwrappedContext !is Activity && unwrappedContext is ContextWrapper) {
    unwrappedContext = unwrappedContext.baseContext
  }
  return unwrappedContext as Activity
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Activity?.addViewOnTheVeryTop(view: View) {
  this?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)?.addView(view)
}

fun Activity?.removeViewFromTheVeryTop(view: View) {
  this?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)?.removeView(view)
}