package com.aleksejantonov.feature.beerlist.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.beerlist.impl.R
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponent
import kotlinx.android.synthetic.main.fragment_beer_list.*

class BeerListFragment : BaseFragment(R.layout.fragment_beer_list) {

  private val viewModel by viewModels<BeerListViewModel> {
    ComponentsManager.get<FeatureBeerListComponent>(requireNotNull(arguments?.getLong(COMPONENT_KEY))).viewModelFactory()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    details.setOnClickListener { viewModel.navigateToDetails(1, "Baltica", null) }
  }

  companion object {

    fun create(componentKey: Long) = BeerListFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}