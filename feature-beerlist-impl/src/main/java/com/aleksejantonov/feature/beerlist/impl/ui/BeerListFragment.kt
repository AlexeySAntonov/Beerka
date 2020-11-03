package com.aleksejantonov.feature.beerlist.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.beerlist.impl.R
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerListFragment : BaseFragment(R.layout.fragment_beer_list) {

  private val viewModel by viewModels<BeerListViewModel> {  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    details.setOnClickListener {

    }
  }

  companion object {

    fun create(componentKey: Long) = BeerListFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}