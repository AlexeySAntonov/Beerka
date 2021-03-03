package com.aleksejantonov.beerka.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.aleksejantonov.core.navigation.navigation.PagerNavigation

class NonSwipeableViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs), PagerNavigation.Pager {

    override var current: Int
        get() = currentItem
        set(value) {
            currentItem = value
        }

    override val isActive: Boolean
        get() = current == 0

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isActive && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isActive && super.onTouchEvent(ev)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return isActive && super.canScrollHorizontally(direction)
    }
}