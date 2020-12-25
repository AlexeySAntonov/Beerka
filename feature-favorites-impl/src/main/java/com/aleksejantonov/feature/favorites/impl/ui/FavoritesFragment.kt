package com.aleksejantonov.feature.favorites.impl.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.adapter.SimpleDiffAdapter
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingDelegate
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingItem
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.trueViewModels
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.feature.favorites.impl.R
import com.aleksejantonov.feature.favorites.impl.ui.delegate.FavoriteItemDelegate
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

  private val viewModel by trueViewModels<FavoritesViewModel>()
  private val adapter by lazy { FavoritesAdapter { viewModel.navigateToDetails(it) } }

  private val scrollListener by lazy {
    object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager? ?: return
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount
        if (lastPosition > itemCount - 2) {
          viewModel.loadMore()
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
    viewModel.data.observe { adapter.items = it }
  }

  override fun onStart() {
    super.onStart()
    recyclerView.addOnScrollListener(scrollListener)
  }

  override fun onStop() {
    recyclerView.removeOnScrollListener(scrollListener)
    super.onStop()
  }

  override fun onStatusBarHeight(statusBarHeight: Int) {
    recyclerView.setMargins(top = statusBarHeight)
  }

  override fun onNavigationBarHeight(navBarHeight: Int) {
    recyclerView.setMargins(bottom = navBarHeight)
  }

  private fun initRecyclerView() {
    with(recyclerView) {
      (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
          return when (this@FavoritesFragment.adapter.items[position]) {
            is PaginationLoadingItem -> 2
            else -> 1
          }
        }
      }
      adapter = this@FavoritesFragment.adapter
    }
  }

  private class FavoritesAdapter(onItemClick: (BeerItem) -> Unit) : SimpleDiffAdapter() {
    init {
      delegatesManager
        .addDelegate(PaginationLoadingDelegate())
        .addDelegate(FavoriteItemDelegate(onItemClick))
    }
  }

  companion object {

    fun create(componentKey: Long) = FavoritesFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}