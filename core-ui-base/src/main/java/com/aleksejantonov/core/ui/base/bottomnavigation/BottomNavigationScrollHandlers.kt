package com.aleksejantonov.core.ui.base.bottomnavigation

import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

object BottomNavigationScrollHandlers {

  class RecyclerViewListener(private val viewModel: BottomNavigationViewModel) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      viewModel.isVisible = dy <= 0
    }
  }

  class NestedScrollViewListener(private val viewModel: BottomNavigationViewModel) : NestedScrollView.OnScrollChangeListener {
    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
      viewModel.isVisible = scrollY - oldScrollY <= 0
    }
  }
}