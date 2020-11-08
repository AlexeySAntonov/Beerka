package com.aleksejantonov.feature.beerlist.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.adapter.SimpleDiffAdapter
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingDelegate
import com.aleksejantonov.core.ui.base.mvvm.trueViewModels
import com.aleksejantonov.feature.beerlist.impl.R
import com.aleksejantonov.feature.beerlist.impl.ui.delegate.BeerItemDelegate
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerListFragment : BaseFragment(R.layout.fragment_beer_list) {

  private val viewModel by trueViewModels<BeerListViewModel>()
  private val adapter by lazy { BeersAdapter() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.adapter = adapter
    viewModel.data.observe { adapter.items = it }
  }

  private class BeersAdapter : SimpleDiffAdapter() {
    init {
      delegatesManager
        .addDelegate(PaginationLoadingDelegate())
        .addDelegate(BeerItemDelegate())
    }
  }

  companion object {

    fun create(componentKey: Long) = BeerListFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}