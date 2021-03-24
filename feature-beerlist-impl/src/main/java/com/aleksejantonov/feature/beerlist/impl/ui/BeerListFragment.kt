package com.aleksejantonov.feature.beerlist.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.feature.beerlist.impl.R
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentsHolder
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerListFragment : BaseFragment(R.layout.fragment_beer_list) {

  private val viewModel by viewModels<BeerListViewModel> {
    (FeatureBeerListComponentsHolder.get(requireNotNull(arguments?.getString(COMPONENT_KEY))) as ViewModelFactoryProvider).viewModelFactory()
  }
  private val adapter by lazy {
    BeersAdapter(
      onBeerClick = { viewModel.navigateToDetails(it) },
      onFavoriteClick = { viewModel.toggleFavorite(it) }
    )
  }

  private val scrollListener by lazy {
    object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager? ?: return
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
    recyclerView.adapter = adapter
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
    recyclerView.setPaddings(bottom = navBarHeight + tabNavigationBarHeight)
  }

  override fun getScrollableViewToChangeBottomNavigationState(): View? = recyclerView

  companion object {

    fun create(componentKey: String) = BeerListFragment().apply {
      arguments = Bundle().apply { putString(COMPONENT_KEY, componentKey) }
    }
  }
}