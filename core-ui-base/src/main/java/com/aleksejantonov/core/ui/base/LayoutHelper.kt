package com.aleksejantonov.core.ui.base

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.aleksejantonov.core.ui.base.mvvm.dpToPx

object LayoutHelper {

    const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    private fun getSize(context: Context, size: Int): Int {
        return if (size < 0) size else context.dpToPx(size.toFloat())
    }

    fun getFrameParams(
        context: Context,
        width: Int = MATCH_PARENT,
        height: Int = WRAP_CONTENT,
        gravity: Int = Gravity.CENTER,
        leftMargin: Int = 0,
        topMargin: Int = 0,
        rightMargin: Int = 0,
        bottomMargin: Int = 0
    ): FrameLayout.LayoutParams {
        val layoutParams = FrameLayout.LayoutParams(getSize(context, width), getSize(context, height), gravity)
        layoutParams.setMargins(
            context.dpToPx(leftMargin.toFloat()),
            context.dpToPx(topMargin.toFloat()),
            context.dpToPx(rightMargin.toFloat()),
            context.dpToPx(bottomMargin.toFloat())
        )
        return layoutParams
    }

    fun getLinearParams(
        context: Context,
        width: Int = MATCH_PARENT,
        height: Int = WRAP_CONTENT,
        weight: Float = 1f,
        gravity: Int = Gravity.CENTER,
        leftMargin: Int = 0,
        topMargin: Int = 0,
        rightMargin: Int = 0,
        bottomMargin: Int = 0
    ): LinearLayout.LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(getSize(context, width), getSize(context, height), weight)
        layoutParams.setMargins(
            context.dpToPx(leftMargin.toFloat()),
            context.dpToPx(topMargin.toFloat()),
            context.dpToPx(rightMargin.toFloat()),
            context.dpToPx(bottomMargin.toFloat())
        )
        layoutParams.gravity = gravity
        return layoutParams
    }
}
